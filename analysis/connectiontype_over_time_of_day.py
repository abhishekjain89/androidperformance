import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.connectiontype_over_time_of_day())


data = tuple_gen.group_by(result,0)

wifi_ticks = tuple_gen.hour_minute_period(data['True'],1,2,10)
wifi_vals =(tuple_gen.base_int(data['True'],3))
mobile_ticks = tuple_gen.hour_minute_period(data['False'],1,2,10)
mobile_vals =(tuple_gen.base_int(data['False'],3))

plt.figure(1)
plt.plot(wifi_ticks,wifi_vals,label='Wifi')
plt.plot(mobile_ticks,mobile_vals,label='Mobile')
plt.ylabel('Count')
plt.title('Connection Type over day')
plt.legend()


plt.show()