import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt

result={}
result['morning'] = database.query(query.wifi_favorite_count_over_time(0,6))
result['afternoon'] = database.query(query.wifi_favorite_count_over_time(12,16))
result['night'] = database.query(query.wifi_favorite_count_over_time(20,24))

plt.title("Favorite wifi router count over time for different time of days")
plt.ylabel('count')
plt.xlabel('time of day')

for key in result:
	x_ticks = tuple_gen.hour_minute_period(result[key],0,1,10)
	y_vals = tuple_gen.base_int(result[key],2)
	
	plt.plot(x_ticks,y_vals,label=key)

plt.legend()
plt.show()



