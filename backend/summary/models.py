# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#     * Rearrange models' order
#     * Make sure each model has one field with primary_key=True
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [appname]'
# into your database.

from django.db import models

class Sim(models.Model):
    serialnumber = models.CharField(max_length=40, primary_key=True)
    state = models.CharField(max_length=20)
    operatorcode = models.CharField(max_length=8)
    operatorname = models.CharField(max_length=20)
    networkcountry = models.CharField(max_length=5)
    class Meta:
        db_table = u'sim'
        
class Device(models.Model):
    deviceid = models.CharField(max_length=40, primary_key=True)
    phonetype = models.CharField(max_length=20)
    phonenumber = models.CharField(max_length=40)
    softwareversion = models.CharField(max_length=10)
    phonemodel = models.CharField(max_length=20)
    androidversion = models.CharField(max_length=10)
    phonebrand = models.CharField(max_length=20)
    devicedesign = models.CharField(max_length=20)
    manufacturer = models.CharField(max_length=20)
    productname = models.CharField(max_length=20)
    radioversion = models.CharField(max_length=20)
    boardname = models.CharField(max_length=20)
    serialnumber = models.ForeignKey(Sim, to_field='serialnumber', db_column='serialnumber')
    datacap = models.IntegerField()
    billingcycle = models.IntegerField()
    networkcountry = models.CharField(max_length=2)
    networkname = models.CharField(max_length=25)
    class Meta:
        db_table = u'device'
        
class Measurement(models.Model):
    measurementid = models.AutoField(primary_key=True)
    time = models.DateTimeField()
    localtime = models.DateTimeField()
    deviceid = models.ForeignKey(Device, to_field='deviceid', db_column='deviceid')
    ismanual = models.BooleanField()
    class Meta:
        db_table = u'measurement'

class WifiHotspot(models.Model):
    macaddress = models.CharField(max_length=40, primary_key=True)
    ssid = models.CharField(max_length=40)
    frequency = models.IntegerField()
    capability = models.CharField(max_length=20)
    class Meta:
        db_table = u'wifi_hotspot'


class Wifi(models.Model):
    measurementid = models.IntegerField(primary_key=True)
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
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')

    class Meta:
        db_table = u'wifi_neighbor'

class Usage(models.Model):
    measurementid = models.IntegerField(primary_key=True)
    total_sent = models.BigIntegerField()
    total_recv = models.BigIntegerField()
    total_till_now = models.BigIntegerField()
    mobile_sent = models.BigIntegerField()
    mobile_recv = models.BigIntegerField()
    mobile_till_now = models.BigIntegerField()
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
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')
    isrunning = models.BooleanField()
    total_diff = models.BigIntegerField()
    class Meta:
        db_table = u'application_use'

class Battery(models.Model):
    measurementid = models.IntegerField(primary_key=True)
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
    measurementid = models.IntegerField(primary_key=True)
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
    measurementid = models.IntegerField(primary_key=True)
    uplinkid = models.ForeignKey(Link,related_name='throughput_linkid',db_column='uplinkid')
    downlinkid = models.ForeignKey(Link,related_name='throughput_downlinkid',db_column='downlinkid')
    class Meta:
        db_table = u'throughput'


class Screen(models.Model):
    screenid = models.AutoField(primary_key=True)
    deviceid = models.CharField(max_length=40)
    time = models.DateTimeField()
    localtime = models.DateTimeField()
    turnedon = models.BooleanField()
    
    class Meta:
        db_table = u'screen'
        
class Cell(models.Model):
    cellid = models.CharField(max_length=20, primary_key=True)
    celltype = models.CharField(max_length=10)
    celllac = models.CharField(max_length=20)
    longitude = models.FloatField()
    latitude = models.FloatField()
    systemid = models.IntegerField()
    networkid = models.IntegerField()
    class Meta:
        db_table = u'cell'

class KillList(models.Model):
    deviceid = models.CharField(max_length=40, primary_key=True)
    class Meta:
        db_table = u'kill_list'



class Network(models.Model):
    measurementid = models.IntegerField(primary_key=True)
    networktype = models.CharField(max_length=10)
    connectiontype = models.CharField(max_length=10)    
    wifistate = models.CharField(max_length=20)
    datastate = models.CharField(max_length=30)
    dataactivity = models.CharField(max_length=30)
    signalstrength = models.CharField(max_length=3)
    cellid = models.CharField(max_length=20)
    celltype = models.CharField(max_length=10)
    celllac = models.CharField(max_length=20)
    longitude = models.FloatField()
    latitude = models.FloatField()
    systemid = models.IntegerField()
    networkid = models.IntegerField()
    class Meta:
        db_table = u'network'

class State(models.Model):
    measurementid = models.IntegerField(primary_key=True)
    cellid = models.CharField(max_length=20)
    deviceid = models.CharField(max_length=40)
    networktype = models.CharField(max_length=20)
    timeslice = models.IntegerField()
    weekday = models.IntegerField()
    class Meta:
        db_table = u'state'

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

class Lastmile(models.Model):
    avg = models.FloatField()
    stdev = models.FloatField()
    min = models.FloatField()
    max = models.FloatField()
    scrip = models.CharField(max_length=20)
    dstip = models.CharField(max_length=20)
    firstip = models.CharField(max_length=20)
    time = models.DateTimeField()
    hopcount = models.IntegerField()
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')
    lastmileid = models.AutoField(primary_key=True)
    class Meta:
        db_table = u'lastmile'

class Values(models.Model):
    valueid = models.AutoField(primary_key=True)
    frequency_secs = models.IntegerField()
    throughput_freq = models.IntegerField()
    uplink_port = models.IntegerField()
    uplink_duration = models.IntegerField()
    downlink_port = models.IntegerField()
    downlink_duration = models.IntegerField()
    tcp_headersize = models.IntegerField()
    tcp_packetsize = models.IntegerField()
    throughput_server_address = models.TextField()
    api_server_address = models.TextField()
    signalstrength_timeout = models.IntegerField()
    wifi_timeout = models.IntegerField()
    unavailable_cellid = models.CharField(max_length=10)
    unavailable_celllac = models.CharField(max_length=10)
    threadpool_max_size = models.IntegerField()
    threadpool_keepalive_sec = models.IntegerField()
    class Meta:
        db_table = u'values'

class PingServers(models.Model):
    ipaddress = models.CharField(max_length=20, primary_key=True)
    tag = models.CharField(max_length=20)
    class Meta:
        db_table = u'ping_servers'
        
class CalculateLog(models.Model):
    log_time = models.DateTimeField(primary_key=True)
    time = models.DateTimeField()
    class Meta:
        db_table = u'calculate_log'

class ErrorLog(models.Model):
    log_time = models.DateTimeField(primary_key=True)
    deviceid = models.CharField(max_length=40)
    error_text = models.TextField()
    user_agent = models.TextField()
    remote_addr = models.CharField(max_length=20)
    class Meta:
        db_table = u'error_log'
        
class WarmupExperiment(models.Model):
    lowest = models.FloatField(null=True, blank=True)
    highest = models.FloatField(null=True, blank=True)
    version = models.CharField(max_length=10, blank=True)
    dstip = models.CharField(max_length=20, blank=True)
    total_count = models.IntegerField(null=True, blank=True)
    gap = models.FloatField(null=True, blank=True)
    measurementid = models.IntegerField(primary_key=True)
    class Meta:
        db_table = u'warmup_experiment'

class WarmupPing(models.Model):
    value = models.FloatField(null=True, blank=True)
    sequence_count = models.IntegerField(null=True, blank=True)
    period = models.FloatField(null=True, blank=True)
    warmupid = models.AutoField(primary_key=True)
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')
    class Meta:
        db_table = u'warmup_ping'

class ClientLog(models.Model):
    log_time = models.DateTimeField(primary_key=True)
    deviceid = models.CharField(max_length=40)
    error_text = models.TextField()
    tag = models.TextField()
    value = models.IntegerField(null=True, blank=True)
    class Meta:
        db_table = u'client_log'

class Loss(models.Model):
    total = models.IntegerField()
    lost = models.IntegerField()
    losspercentage = models.FloatField()
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')
    lossid = models.AutoField(primary_key=True)
    class Meta:
        db_table = u'loss'

class Ipdv(models.Model):
    sequencenumber = models.IntegerField()
    ipdv = models.IntegerField()
    measurementid = models.ForeignKey(Measurement, to_field='measurementid', db_column='measurementid')
    ipdvid = models.AutoField(primary_key=True)
    class Meta:
        db_table = u'ipdv'


