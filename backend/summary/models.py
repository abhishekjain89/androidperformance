# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#     * Rearrange models' order
#     * Make sure each model has one field with primary_key=True
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [appname]'
# into your database.

from django.db import models

class WifiHotspot(models.Model):
    macaddress = models.CharField(max_length=18, primary_key=True)
    ssid = models.CharField(max_length=30)
    frequency = models.IntegerField()
    capability = models.CharField(max_length=20)
    class Meta:
        db_table = u'wifi_hotspot'


class Wifi(models.Model):
    wifiid = models.AutoField(primary_key=True)
    ipaddress = models.CharField(max_length=15)
    detailedinfo = models.CharField(max_length=40)
    rssi = models.IntegerField()
    signalstrength = models.IntegerField()
    speed = models.IntegerField()
    units = models.CharField(max_length=10)
    connection = models.ForeignKey(WifiHotspot,  to_field='macaddress', db_column='connection')
    class Meta:
        db_table = u'wifi'


class WifiNeighbor(models.Model):
    wifi_neighbor_id = models.AutoField(primary_key=True)
    macaddress = models.ForeignKey(WifiHotspot,  to_field='macaddress', db_column='macaddress')
    ispreferred = models.IntegerField()
    signallevel = models.IntegerField()
    wifiid = models.ForeignKey(Wifi, to_field='wifiid', db_column='wifiid')
    class Meta:
        db_table = u'wifi_neighbor'

class Usage(models.Model):
    usageid = models.AutoField(primary_key=True)
    total_sent = models.BigIntegerField()
    total_recv = models.BigIntegerField()
    mobile_sent = models.BigIntegerField()
    mobile_recv = models.BigIntegerField()
    class Meta:
        db_table = u'usage'

class Application(models.Model):
    name = models.CharField(max_length=20)
    package = models.CharField(max_length=50, primary_key=True)
    class Meta:
        db_table = u'application'

class ApplicationUse(models.Model):
    application_useid = models.AutoField(primary_key=True)
    package = models.ForeignKey(Application, to_field='package', db_column='package')
    total_sent = models.BigIntegerField()
    total_recv = models.BigIntegerField()
    usageid = models.ForeignKey(Usage, to_field='usageid', db_column='usageid')
    class Meta:
        db_table = u'application_use'

class Battery(models.Model):
    batteryid = models.AutoField(primary_key=True)
    technology = models.CharField(max_length=20)
    ispresent = models.IntegerField()
    plugged = models.IntegerField()
    scale = models.IntegerField()
    health = models.IntegerField()
    voltage = models.IntegerField()
    level = models.IntegerField()
    temperature = models.IntegerField()
    status = models.IntegerField()
    class Meta:
        db_table = u'battery'


class Gps(models.Model):
    gpsid = models.AutoField(primary_key=True)
    latitude = models.FloatField()
    longitude = models.FloatField()
    altitude = models.FloatField()
    class Meta:
        db_table = u'gps'


class Link(models.Model):
    linkid = models.AutoField(primary_key=True)
    count = models.IntegerField()
    message_size = models.BigIntegerField()
    duration = models.IntegerField()
    speed = models.FloatField()
    port = models.IntegerField()
    ip_address = models.CharField(max_length=20)
    class Meta:
        db_table = u'link'

class Throughput(models.Model):
    throughputid = models.AutoField(primary_key=True)
    uplinkid = models.ForeignKey(Link,related_name='throughput_linkid',db_column='uplinkid')
    downlinkid = models.ForeignKey(Link,related_name='throughput_downlinkid',db_column='downlinkid')
    class Meta:
        db_table = u'throughput'


class Sim(models.Model):
    serialnumber = models.CharField(max_length=20, primary_key=True)
    state = models.CharField(max_length=20)
    operatorcode = models.CharField(max_length=8)
    operatorname = models.CharField(max_length=20)
    networkcountry = models.CharField(max_length=5)
    class Meta:
        db_table = u'sim'

class Device(models.Model):
    deviceid = models.CharField(max_length=20, primary_key=True)
    phonetype = models.CharField(max_length=20)
    phonenumber = models.CharField(max_length=15)
    softwareversion = models.CharField(max_length=10)
    phonemodel = models.CharField(max_length=20)
    androidversion = models.CharField(max_length=10)
    phonebrand = models.CharField(max_length=20)
    devicedesign = models.CharField(max_length=20)
    manufacturer = models.CharField(max_length=20)
    productname = models.CharField(max_length=20)
    radioversion = models.CharField(max_length=20)
    boardname = models.CharField(max_length=20)
    class Meta:
        db_table = u'device'

class Network(models.Model):
    networkid = models.AutoField(primary_key=True)
    networkcountry = models.CharField(max_length=2)
    networkname = models.CharField(max_length=15)
    networktype = models.CharField(max_length=10)
    connectiontype = models.CharField(max_length=10)
    mobilenetworkinfo = models.TextField()
    wifistate = models.CharField(max_length=20)
    cellid = models.CharField(max_length=20)
    celllac = models.CharField(max_length=20)
    datastate = models.CharField(max_length=30)
    dataactivity = models.CharField(max_length=30)
    signalstrength = models.CharField(max_length=3)
    class Meta:
        db_table = u'network'


class Measurement(models.Model):
    measurementid = models.AutoField(primary_key=True)
    time = models.DateTimeField()
    deviceid = models.ForeignKey(Device, to_field='deviceid', db_column='deviceid')
    networkid = models.ForeignKey(Network, to_field='networkid', db_column='networkid')
    serialnumber = models.ForeignKey(Sim, to_field='serialnumber', db_column='serialnumber')
    throughputid = models.ForeignKey(Throughput, to_field='throughputid', db_column='throughputid')
    gpsid = models.ForeignKey(Gps, to_field='gpsid', db_column='gpsid')
    batteryid = models.ForeignKey(Battery, to_field='batteryid', db_column='batteryid')
    usageid = models.ForeignKey(Usage, to_field='usageid', db_column='usageid')
    wifiid = models.ForeignKey(Wifi, to_field='wifiid', db_column='wifiid')
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
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')
    pingid = models.AutoField(primary_key=True)
    class Meta:
        db_table = u'ping'


