from django.contrib import admin
from django.urls import path
from rest_framework import routers

urlpatterns = [
    path('admin/', admin.site.urls),
]

router = routers.SimpleRouter()
urlpatterns += router.urls
