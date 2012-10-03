select networktype,signalstrength,count(*)
from network
where networktype!='UNKNOWN' and signalstrength!='' and networktype!='' and connectiontype!='' and connectiontype!='Wifi'
group by networktype,signalstrength;
