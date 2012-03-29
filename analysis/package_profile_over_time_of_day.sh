import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt
pkgs = ('com.android.browser','com.google.android.youtube','com.facebook.katana','com.android.pulse')
plt.figure(1)
subplot_count=1
size = int(len(pkgs) ** 0.5)

for pkg in pkgs:
	print pkg
	result = database.query(query.package_profile_over_time_of_day(pkg))
	pprint.pprint(result)
	plt.subplot(size,size,subplot_count)
	
	plt.title(pkg)
	x_ticks = tuple_gen.hour_minute_period(result,0,1,10)
	total_vals = tuple_gen.MB(result,2)
	plt.ylabel('MB')
	plt.plot(x_ticks,total_vals)
	subplot_count+=1
plt.show()