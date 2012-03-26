import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.usage_over_hour_of_day_finegrain())

pprint.pprint(result)


x_ticks = tuple_gen.hour_minute_period(result,0,1,10)
wifi_vals = tuple_gen.normalize(tuple_gen.MB(result,2))
mobile_vals = tuple_gen.normalize(tuple_gen.MB(result,3))


plt.figure(1)
plt.subplot(211)
plt.plot(x_ticks,wifi_vals)

plt.ylabel('MB')
plt.title('Wifi usage over day')

plt.subplot(212)
plt.plot(x_ticks,mobile_vals)

plt.ylabel('MB')
plt.title('Mobile usage over day')


plt.show()





