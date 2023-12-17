from django.shortcuts import render

# Create your views here.
from rest_framework import generics
from .models import Swimmer
from .serializer import SwimmerSerializer  
from asgiref.sync import async_to_sync
from channels.layers import get_channel_layer


def get_client_ip(request):
    x_forwarded_for = request.META.get('HTTP_X_FORWARDED_FOR')
    if x_forwarded_for:
        ip = x_forwarded_for.split(',')[0]
    else:
        ip = request.META.get('REMOTE_ADDR')
    return ip

class SwimmerListCreateView(generics.ListCreateAPIView):
    queryset = Swimmer.objects.all()
    serializer_class = SwimmerSerializer

    def perform_create(self, serializer):
        serializer.save()

        channel_layer = get_channel_layer()
        async_to_sync(channel_layer.group_send)(
            "swimmers_group",
            {
                "type": "swimmer_update",
                "sender_ip": get_client_ip(self.request),  # Include the sender's channel name
                "message": {
                    "action": "create",
                    "swimmer": serializer.data
                }
            }
        )



class SwimmerRetrieveUpdateDestroyView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Swimmer.objects.all()
    serializer_class = SwimmerSerializer


    def perform_update(self, serializer):
        serializer.save()
        # Notify via WebSocket after updating a swimmer
        channel_layer = get_channel_layer()
        async_to_sync(channel_layer.group_send)(
            "swimmers_group",
            {
                "type": "swimmer_update",
                "sender_ip": get_client_ip(self.request),
                "message": {
                    "action": "update",
                    "swimmer": serializer.data
                }
            }
        )

    def perform_destroy(self, instance):
        # Notify via WebSocket before deleting a swimmer
        channel_layer = get_channel_layer()
        swimmer_data = SwimmerSerializer(instance).data
        instance.delete()
        async_to_sync(channel_layer.group_send)(
            "swimmers_group",
            {
                "type": "swimmer_update",
                "sender_ip": get_client_ip(self.request),
                "message": {
                    "action": "delete",
                    "swimmer": swimmer_data
                }
            }
        )



