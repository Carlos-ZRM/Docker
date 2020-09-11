
from mongoengine import connect


connect(
    db='test',
    username='mongoadmin',
    password='secret',
    host='0.0.0.0', 
    authentication_source='admin',
    authMechanism='SCRAM-SHA-1'
    )
