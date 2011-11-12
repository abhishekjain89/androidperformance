# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#     * Rearrange models' order
#     * Make sure each model has one field with primary_key=True
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [appname]'
# into your database.

from django.db import models

class Device(models.Model):
    deviceid = models.CharField(max_length=20, primary_key=True)
    class Meta:
        db_table = u'device'


class Measurement(models.Model):
    measurementid = models.CharField(max_length=20, primary_key=True)
    deviceid = models.ForeignKey(Device, db_column='deviceid')
    simoperatorcode = models.CharField(max_length=20)
    networktype = models.CharField(max_length=10)
    simserialnumber = models.FloatField()
    phonetype = models.FloatField()
    altitude = models.CharField(max_length=10)
    networkcountry = models.CharField(max_length=10)
    connectiontype = models.CharField(max_length=10)
    simnetworkcountry = models.CharField(max_length=10)
    networkoperatorid = models.IntegerField()
    mobilenetworkdetailedstate = models.CharField(max_length=10)
    simstate = models.CharField(max_length=5)
    time = models.DateTimeField()
    mobilenetworkstate = models.CharField(max_length=10)
    longitude = models.CharField(max_length=10)
    latitude = models.CharField(max_length=10)
    simoperatorname = models.CharField(max_length=20)
    networkname = models.CharField(max_length=20)
    class Meta:
        db_table = u'measurement'


class Ping(models.Model):
    avg = models.FloatField()
    stdev = models.FloatField()
    min = models.FloatField()
    max = models.FloatField()
    scrip = models.CharField(max_length=20)
    dstip = models.CharField(max_length=20)
    time = models.DateTimeField()
    measurementid = models.ForeignKey(Measurement, db_column='measurementid')
    pingid = models.CharField(max_length=20, primary_key=True)
    class Meta:
        db_table = u'ping'
