from rest_framework import serializers
from .models import Swimmer

class SwimmerSerializer(serializers.ModelSerializer):
    class Meta:
        model = Swimmer
        fields = ['id', 'fullname', 'gender', 'nationality', 'weight', 'height']
