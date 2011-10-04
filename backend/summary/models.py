# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#     * Rearrange models' order
#     * Make sure each model has one field with primary_key=True
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [appname]'
# into your database.

from django.db import models

class Devices(models.Model):
    deviceid = models.TextField(primary_key=True) # This field type is a guess.
    username = models.TextField() # This field type is a guess.
    carrier = models.TextField() # This field type is a guess.
    plantype = models.TextField() # This field type is a guess.
    register = models.DateTimeField()
    last_update = models.DateTimeField()
    class Meta:
        db_table = u'devices'

class MThroughput(models.Model):
    deviceid = models.TextField() # This field type is a guess.
    srcip = models.IPAddressField()
    dstip = models.IPAddressField()
    eventstamp = models.DateTimeField()
    average = models.FloatField()
    std = models.FloatField()
    minimum = models.FloatField()
    maximum = models.FloatField()
    median = models.FloatField()
    type = models.CharField(max_length=20)
    class Meta:
        db_table = u'm_throughput'

class MRtt(models.Model):
    deviceid = models.TextField() # This field type is a guess.
    srcip = models.IPAddressField()
    dstip = models.IPAddressField()
    eventstamp = models.DateTimeField()
    average = models.FloatField()
    std = models.FloatField()
    minimum = models.FloatField()
    maximum = models.FloatField()
    median = models.FloatField()
    type = models.CharField(max_length=20)
    class Meta:
        db_table = u'm_rtt'

