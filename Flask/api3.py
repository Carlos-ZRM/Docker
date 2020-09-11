from flask import Flask
from flask_restful import reqparse, abort, Api, Resource
from flask_mongoengine import MongoEngine, MongoEngineSessionInterface

from database.db import initialize_db
from database.models import Usuario
from flask_pymongo import PyMongo
from flask import request

#MONGODB_HOST = os.environ["MONGODB_HOST"]
#MONGODB_PORT = os.environ["MONGODB_PORT"]
#MONGO_INITDB_ROOT_USERNAME = os.environ["MONGO_INITDB_ROOT_USERNAME"]
#MONGO_INITDB_ROOT_PASSWORD = os.environ["MONGO_INITDB_PASSWORD"]

#MONGODB_HOST = 'mongodb://0.0.0.0/'
#MONGODB_PORT = '27017'
#MONGO_INITDB_ROOT_USERNAME='mongoadmin'
#MONGO_INITDB_ROOT_PASSWORD='secret'
'''
CONFIG_MONGO = {
    'db':'test',
    'host': MONGODB_HOST,
    'port': MONGODB_PORT,
    'username':MONGO_INITDB_ROOT_USERNAME,
    'password':MONGO_INITDB_ROOT_PASSWORD
}

'''

#print(CONFIG_MONGO)

TODOS = {
    'todo1': {'task': 'build an API'},
    'todo2': {'task': '?????'},
    'todo3': {'task': 'profit!'},
}
errors = {
    'UserAlreadyExistsError': {
        'message': "A user with that username already exists.",
        'status': 409,
    },
    'ResourceDoesNotExist': {
        'message': "A resource with that ID no longer exists.",
        'status': 410,
        'extra': "Any extra information you want.",
    },
}



app = Flask(__name__)

app.config["MONGO_URI"] = "mongodb://localhost:27017/taller"
mongo = PyMongo(app)

api = Api(app, errors=errors)

#app.config['MONGODB_SETTINGS']  = CONFIG_MONGO

#db = MongoEngine(app)
#app.session_interface = MongoEngineSessionInterface(db)

#initialize_db(app)

def abort_if_todo_doesnt_exist(todo_id):
    if todo_id not in TODOS:
        abort(404, message="Todo {} doesn't exist".format(todo_id))
        #abort(e.code, str(e))

parser = reqparse.RequestParser()
parser.add_argument('pokemon')
parser.add_argument('carrera')
parser.add_argument('semestre')


# Todo
# shows a single todo item and lets you delete a todo item
class Todo(Resource):
    def get(self, todo_id):
        abort_if_todo_doesnt_exist(todo_id)
        return TODOS[todo_id]

    def delete(self, todo_id):
        abort_if_todo_doesnt_exist(todo_id)
        del TODOS[todo_id]
        return '', 204

    def put(self, todo_id):
        args = parser.parse_args()
        task = {'task': args['task']}
        TODOS[todo_id] = task


        return task, 201


# TodoList
# shows a list of all todos, and lets you POST to add new tasks
class TodoList(Resource):
    def get(self):
        #return TODOS
        user = mongo.db.users.find_one_or_404({"_id": username})
        return render_template("user.html", user=user)

    # Post Save file 
    def post(self):
        args = parser.parse_args()
        print("args",args, type(args))
        mongo.save_file(args['pokemon'], request.files["file"])

        todo_id = int(max(TODOS.keys()).lstrip('todo')) + 1
        todo_id = 'todo%i' % todo_id
        TODOS[todo_id] = {'task': args['pokemon']}
        return redirect(url_for("get_upload", filename=filename))
        return {'hello': 'world'}
        return TODOS[todo_id], 201

class Barra(Resource):
    def get(self):
        try:
            return {'IP Remota': request.remote_addr, 'IP Host':request.environ['SERVER_NAME']} , 200

        except APIException as e:
            abort(e.code, str(e))
##
## Actually setup the Api resource routing here
##
api.add_resource(TodoList, '/todos')
api.add_resource(Todo, '/todoos/<todo_id>')
api.add_resource(Barra, '/')

if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0')
