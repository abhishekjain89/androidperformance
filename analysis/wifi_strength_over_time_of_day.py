import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.wifi_strength_over_time())

plt.ylim(0,1)
plt.title("Wifi strength")
plt.ylabel('strength')
plt.xlabel('time')
x_ticks = tuple_gen.hour_minute_period(result,0,1,10)
y_vals = tuple_gen.normalize(tuple_gen.base_float(result,2))

plt.plot(x_ticks,y_vals)

	
plt.show()



