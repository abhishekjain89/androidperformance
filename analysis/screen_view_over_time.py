import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.screen_open_over_time())


plt.title("Screen View")
plt.ylabel('count')
plt.xlabel('time')
x_ticks = tuple_gen.hour_minute_period(result,0,1,10)
y_vals = tuple_gen.base_float(result,2)

plt.plot(x_ticks,y_vals)

	
plt.show()



