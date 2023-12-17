from django.urls import re_path

from swimming_api.consumer import SwimmerConsumer


websocket_urlpatterns = [
    re_path(r'ws/swimmers/$', SwimmerConsumer.as_asgi()),
]