import os

from bson.objectid import ObjectId
# pprint library is used to make the output look more pretty
from pprint import pprint
from pymongo import MongoClient
import models

#MONGODB_HOST = os.environ["MONGODB_HOST"]
        #MONGODB_PORT = os.environ["MONGODB_PORT"]

##MONGO_INITDB_ROOT_USERNAME = os.environ["MONGO_INITDB_ROOT_USERNAME"]
##MONGO_INITDB_ROOT_PASSWORD = os.environ["MONGO_INITDB_ROOT_PASSWORD"]
        
MONGODB_HOST = '0.0.0.0'
MONGODB_PORT = '27017'
MONGO_INITDB_ROOT_USERNAME='mongoadmin'
MONGO_INITDB_ROOT_PASSWORD='secret'

##MONGODB_HOST = 'MONGODB_HOST'

MONGODB_TIMEOUT = 1000

URI_CONNECTION = "mongodb://" + MONGODB_HOST + ":" + MONGODB_PORT +  "/"
class Usuario(mongoengine.Document):
    pokemon = mongoengine.StringField(required=True)
    carrera = mongoengine.StringField()
    semestre = mongoengine.IntField()
        
class  DB :
    def __init__(self):
        # Abrir conexion

        # connect to MongoDB, change the << MONGODB URL >> to reflect your own connection string
        try :
            self.cliente = MongoClient(URI_CONNECTION, username = MONGO_INITDB_ROOT_USERNAME ,
                                password= MONGO_INITDB_ROOT_PASSWORD, 
                                serverSelectionTimeoutMS=MONGODB_TIMEOUT,maxPoolSize=10)

            self.db=self.cliente.registro
            self.coleccion = self.db.pokemones
            
            
            #print(self.coleccion.find_one())
            #pprint(self.coleccion.find_one({'_id':ObjectId('5f55c88a108b56a1541d612d')}))
            #pokemon carrera semestee     
        except Exception as error:
            print ("Error saving data: %s" % str(error))

    def put(self, json):
        try:
            doc = self.coleccion.insert_one(json)
            return doc.inserted_id
        except Exception as error:
            print ("Error (PUT) mongo: %s" % str(error))
    def get(self, id):
        try:
            doc = self.coleccion.find_one({'_id':ObjectId(id)})
            return doc
        except Exception as error:
            print ("Error (GET) mongo: %s" % str(error))
    def get_all(self):
        try:
            doc = self.coleccion.find()
            return doc
        except Exception as error:
            print ("Error (GET) mongo: %s" % str(error))
db = DB ()
get = db.get('5f55c88a108b56a1541d612d')
get = db.get_all()
get = [{x['_id'] : x} for x in get ]
print(type(get),get)

#for x in get :
#    print(x, type(x))