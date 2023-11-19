import React, { useState } from 'react';
import { View, Text, ScrollView, Alert } from 'react-native';
import { Button, TextInput } from 'react-native-paper';
import { styles } from './MainScreen';
import Swimmer from '../entities/Swimmer';

interface AddScreenProps {
  navigation : any;
  route : any;
}

const AddScreen = (props : AddScreenProps) => {

  const [fullName, setFullName] = useState("");
  const [gender, setGender] = useState("");
  const [nationality, setNationality] = useState("");
  const [weight, setWeight] = useState("");
  const [height, setHeight] = useState("");

  const handleAdd = () => {

    if (!Number.isInteger(Number(weight))) {
        Alert.alert("Invalid input", "Weight should be a number!");
        return;
    }

    if (!Number.isInteger(Number(height))) {
        Alert.alert("Invalid input", "Height should be a number!");
        return;
    }

    if (fullName == "" || gender == "" || nationality == "" || weight == "" || height == "") {
        Alert.alert("Invalid input", "Make sure all fields re filled in!");
        return;
    }

      const newSwimmer = new Swimmer(0, fullName, gender, nationality, parseFloat(weight), parseInt(height));    
      props.navigation.navigate("Swimmers List", { newSwimmerTeammate : newSwimmer })
  }

  return (
    <ScrollView style={styles.container}>
      <TextInput label = 'Full Name' placeholder='Enter Full Name' style={styles.textInput} underlineColor='transparent' 
      onChangeText={(text) => setFullName(text)}/>
      <TextInput label = 'Gender' placeholder='Enter Gender' style={styles.textInput} underlineColor='transparent'
      onChangeText={(text) => setGender(text)}/>
      <TextInput label = 'Nationality' placeholder='Enter Nationality' style={styles.textInput} underlineColor='transparent'
      onChangeText={(text) => setNationality(text)}/>
      <TextInput label = 'Weight' placeholder='Enter Weight' style={styles.textInput} underlineColor='transparent'
      onChangeText={(text) => setWeight(text)}/>
      <TextInput label = 'Height' placeholder='Enter Height' style={styles.textInput} underlineColor='transparent'
      onChangeText={(text) => setHeight(text)}/>
    <Button style={styles.button} labelStyle={{color: "white"}} onPress={handleAdd}>Add Swimmer</Button>
</ScrollView>
  );
};

export default AddScreen;