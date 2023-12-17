from typing import Any
from django.db import models

# Create your models here.

class Swimmer(models.Model):

    fullname = models.CharField(max_length=200)
    gender = models.CharField(max_length=200)
    nationality = models.CharField(max_length=200)
    weight = models.IntegerField()
    height = models.IntegerField()

    def __str__(self):
        return self.fullname

    class Meta:
        db_table = "swimmer"