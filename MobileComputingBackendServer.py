import os
import io
from flask import Flask, flash, request, redirect, url_for
from requests_toolbelt.multipart import decoder
import PIL.Image as Image

import numpy as np
import keras
from keras.datasets import mnist
from keras.models import Model
from keras.layers import Dense, Input
from keras.layers import Conv2D, MaxPooling2D, Dropout, Flatten
from keras import backend as k
# Importing Image class from PIL module
from PIL import Image

new_model = keras.models.load_model('MCModel')

import cv2

def rgb2gray(rgb):
    return np.dot(rgb[...,:3], [0.2989, 0.5870, 0.1140])

# def crop_img(img, scale=1.0):
#     center_x, center_y = img.shape[1] / 2, img.shape[0] / 2
#     width_scaled, height_scaled = img.shape[1] * scale, img.shape[0] * scale
#     left_x, right_x = center_x - width_scaled / 2, center_x + width_scaled / 2
#     top_y, bottom_y = center_y - height_scaled / 2, center_y + height_scaled / 2
#     img_cropped = img[int(top_y):int(bottom_y), int(left_x):int(right_x)]
#     return img_cropped

def crop_square(img, scale=1.0):
    center_x, center_y = img.shape[1] / 2, img.shape[0] / 2
    scaled = min(img.shape[0], img.shape[1]) * scale
    left_x, right_x = center_x - scaled / 2, center_x + scaled / 2
    top_y, bottom_y = center_y - scaled / 2, center_y + scaled / 2
    img_cropped = img[int(top_y):int(bottom_y), int(left_x):int(right_x)]
    return img_cropped

UPLOAD_FOLDER = '/Users/arushigaur/Desktop/mobile_computing/'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/upload_image', methods=['GET','POST'])
def upload_file():
    tag = "digits"
    print("got request")
    print(request.files["file"])
    filename = request.files["file"].filename


    image_input = Image.open(request.files["file"])

    print("saved image")
    # predicting the label of image
    image = np.array(image_input)


    grayimage = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    w, h = grayimage.shape
    cropimage = crop_square(grayimage, 0.4)
    se=cv2.getStructuringElement(cv2.MORPH_RECT , (8,8))
    bg=cv2.morphologyEx(cropimage, cv2.MORPH_DILATE, se)
    out_gray=cv2.divide(cropimage, bg, scale=255)
    out_binary=cv2.threshold(out_gray, 0, 255, cv2.THRESH_OTSU )[1]
    resize_image_2 = cv2.resize(out_binary, (128, 128), interpolation=cv2.INTER_CUBIC)
    resize_image_2 = cv2.resize(resize_image_2, (100, 100), interpolation=cv2.INTER_CUBIC)
    resize_image_1 = cv2.resize(resize_image_2, (64, 64), interpolation=cv2.INTER_CUBIC)

    resize_image = cv2.resize(resize_image_1, (28, 28), interpolation=cv2.INTER_CUBIC)
    black_background = cv2.bitwise_not(resize_image)


    image = np.copy(black_background).reshape(1,28,28,1)
    image = image.astype('float32')
    image /= 255
    yhat = new_model.predict([image])
    print('Predicted: {}'.format(np.argmax(yhat)))
    val = str(np.argmax(yhat))
    if not os.path.exists(UPLOAD_FOLDER + val):
        print("os: ", str(UPLOAD_FOLDER + val))
        os.makedirs(str(UPLOAD_FOLDER + val))
    image_input.save(UPLOAD_FOLDER + val + "/" + filename)
    return "found"


if __name__ == "__main__":
	app.run(host="0.0.0.0")
