from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from django.conf import settings

admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^dashboard/', include('dashboard.foo.urls')),

    # Uncomment the admin/doc line below and add 'django.contrib.admindocs'
    # to INSTALLED_APPS to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
     (r'^admin/', include(admin.site.urls)),

    # site URLs
     (r'^$', 'backend.summary.views.index'),
     (r'^playground', 'backend.summary.views.playground'),
     (r'^device', 'backend.summary.views.devicesummary'),
     (r'^data/(?P<deviceid>\w+)', 'backend.summary.views.showdata'),
     (r'^details/(?P<measurementid>\w+)', 'backend.summary.views.measurementdetails'),
     (r'^register', 'backend.summary.views.register'),
     (r'^test_request', 'backend.summary.views.test_request'),
     (r'^check_register', 'backend.summary.views.check_register'),
     (r'^measurement_v2', 'backend.summary.views.measurement'),
     (r'^measurement', 'backend.summary.views.old_measurement'),
     (r'^summary', 'backend.summary.views.summary'),
     (r'^log', 'backend.summary.views.clientlog'),
     (r'^values', 'backend.summary.views.values'),
     (r'^calculate', 'backend.summary.views.CalculateTraffic'),     
     (r'^traffic/', 'backend.summary.views.getTraffic'),
     (r'^parameter_check', 'backend.summary.views.parameterCheck'),
     (r'^static/(?P<path>.*)$','django.views.static.serve',{'document_root':settings.MEDIA_ROOT})

)



