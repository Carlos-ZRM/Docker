

class Usuario(mongoengine.Document):
    pokemon = mongoengine.StringField(required=True)
    carrera = mongoengine.StringField()
    semestre = mongoengine.IntField()
