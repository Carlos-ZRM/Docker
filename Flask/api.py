from flask import Flask
from flask_restful import Resource, Api
from flask import jsonify
from flask import request
import os


app = Flask(__name__)
api = Api(app)

class HelloWorld(Resource):
    def get(self):
        return {'hello': 'world'}

api.add_resource(HelloWorld, '/')

@app.route("/get_my_ip", methods=["GET"])
def get_my_ip():
    #print(request.environ)
    return jsonify({'IP Remota': request.remote_addr, 'IP Host':request.environ['SERVER_NAME']  }), 200

if __name__ == '__main__':
    app.run(debug=True, host= os.environ["HOSTNAME"])

