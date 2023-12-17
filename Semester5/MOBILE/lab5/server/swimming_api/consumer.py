from channels.generic.websocket import AsyncWebsocketConsumer
import json

class SwimmerConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.channel_layer.group_add("swimmers_group", self.channel_name)
        await self.accept()

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard("swimmers_group", self.channel_name)

    async def swimmer_update(self, event):
        # Send the message to WebSocket
        if event.get("sender_ip") != self.scope["client"][0]:
            await self.send(text_data=json.dumps(event["message"]))
