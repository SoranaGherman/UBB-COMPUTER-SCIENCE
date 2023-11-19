import React, { useState } from 'react';
import { Alert, ScrollView } from 'react-native';
import { styles } from './MainScreen';
import {Button, TextInput } from 'react-native-paper';

interface EditScreenProps {
  navigation : any;
  route : any;
}

const EditScreen = (props : EditScreenProps) => {

  const selectedSwimmer = props.route.params.paramKey; 

  const [fullName, setFullName] = useState(selectedSwimmer.fullname);
  const [gender, setGender] = useState(selectedSwimmer.gender);
  const [nationality, setNationality] = useState(selectedSwimmer.nationality);
  const [weight, setWeight] = useState(selectedSwimmer.weight.toString());
  const [height, setHeight] = useState(selectedSwimmer.height.toString());

  const handleUpdate = () => {

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

    selectedSwimmer.fullname = fullName;
    selectedSwimmer.gender = gender;
    selectedSwimmer.nationality = nationality;
    selectedSwimmer.weight = weight;
    selectedSwimmer.height = height;
    
    props.navigation.navigate("Swimmers List", { updatedSwimmer : selectedSwimmer })
  }

  return (
    <ScrollView style={styles.container}>
        <TextInput label = 'Full Name' value = {fullName} style={styles.textInput} underlineColor='transparent' 
        onChangeText={(text) => setFullName(text)}/>
        <TextInput label = 'Gender' value = {gender} style={styles.textInput} underlineColor='transparent'
        onChangeText={(text) => setGender(text)}/>
        <TextInput label = 'Nationality' value = {nationality} style={styles.textInput} underlineColor='transparent'
        onChangeText={(text) => setNationality(text)}/>
        <TextInput label = 'Weight' value = {weight} style={styles.textInput} underlineColor='transparent'
        onChangeText={(text) => setWeight(text)}/>
        <TextInput label = 'Height' value = {height} style={styles.textInput} underlineColor='transparent'
        onChangeText={(text) => setHeight(text)}/>
        <Button style={styles.button} labelStyle={{color: "white"}} onPress={handleUpdate}>Update Swimmer</Button>
    </ScrollView>
  );
};

export default EditScreen;