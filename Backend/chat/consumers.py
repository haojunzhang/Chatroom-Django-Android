import json

from asgiref.sync import async_to_sync
from channels.generic.websocket import WebsocketConsumer


class ChatConsumer(WebsocketConsumer):

    def connect(self):
        self.room_group_name = 'default_group'

        # Join room group
        async_to_sync(self.channel_layer.group_add)(
            self.room_group_name,
            self.channel_name
        )
        print(f'connect:{self.room_group_name}:{self.channel_name}')

        print(f'accept')
        self.accept()

    def disconnect(self, close_code):
        print('disconnect')
        # Leave room group
        async_to_sync(self.channel_layer.group_discard)(
            self.room_group_name,
            self.channel_name
        )

    # Receive message from WebSocket
    def receive(self, text_data):
        print(f'receive:{text_data}')

        data = json.loads(text_data)
        user_name = data['user_name']
        message = data['message']

        print(f'send:{text_data}')
        async_to_sync(self.channel_layer.group_send)(
            self.room_group_name,
            {
                'type': 'chat_message',
                'user_name': user_name,
                'message': message
            }
        )

    def chat_message(self, event):
        self.send(text_data=json.dumps({
            'user_name': event['user_name'],
            'message': event['message'],
        }))
