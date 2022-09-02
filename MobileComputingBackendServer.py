import os
import io
from flask import Flask, flash, request, redirect, url_for
from requests_toolbelt.multipart import decoder
import PIL.Image as Image

UPLOAD_FOLDER = '/Users/arushigaur/Desktop/mobile_computing/'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/upload_image', methods=['GET','POST'])
def upload_file():
    tag = request.form["tag"]
    print(request.files["file"])
    filename = request.files["file"].filename

    if not os.path.exists(UPLOAD_FOLDER + tag):
    	os.makedirs(UPLOAD_FOLDER + tag)

    image = Image.open(request.files["file"])
    image.save(UPLOAD_FOLDER + tag + "/" + filename)
    return "found"


if __name__ == "__main__":
	app.run(host="0.0.0.0")
       