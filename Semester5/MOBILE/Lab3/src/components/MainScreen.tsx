import React, { useEffect, useState } from 'react';
import { StyleSheet, ScrollView, ImageBackground } from 'react-native';
import { Card, IconButton, Title } from 'react-native-paper';
import Swimmer from '../entities/Swimmer';


interface MainScreenProps {
  navigation : any;
  route : any;
}

export const renderSwimmerCard = ({swimmer, props} : {swimmer:Swimmer, props : any}) => {

  const handleOnEditButton = () => {props.navigation.navigate("Edit", {paramKey: swimmer})}
  const handleOnDeleteButton = () => {props.navigation.navigate("Delete", {paramKey: swimmer})}

  console.log(swimmer);

  return (
    <>
      <Card key={swimmer.getId()} style={styles.card}>
      <Card.Content>
        <Title>{swimmer.getFullName()}</Title>
      </Card.Content>
      <Card.Actions>
        <IconButton icon = "pencil" iconColor="white" size={20} onPress={handleOnEditButton} style={styles.iconButton}/>
        <IconButton icon = "delete" iconColor="white" size={20} onPress={handleOnDeleteButton} style={styles.iconButton}/>
      </Card.Actions>
    </Card>
    </>
);
  }

const SwimmerListScreen = (props : MainScreenProps) => {
  const updatedSwimmer = props.route.params?.updatedSwimmer;
  const swimmerIndex = props.route.params?.swimmerIndex;
  const newSwimmer : Swimmer= props.route.params?.newSwimmerTeammate;

  const [swimmers, setSwimmers] = useState([
    new Swimmer(0, 'Ruxandra Brehar', 'Female', 'USA', 75, 180),
    new Swimmer(1, 'Dragan Daria', 'Female', 'Canada', 62, 170),
    new Swimmer(2, 'Ropan Stefan', 'Male', 'USA', 75, 180),
    new Swimmer(3, 'David Popovici', 'Male', 'Canada', 62, 170),
    new Swimmer(4, 'Pop Alex', 'Male', 'Canada', 62, 170),
    new Swimmer(5, 'Toma Marius', 'Male', 'Canada', 62, 170),
    new Swimmer(6, 'Marian A', 'Male', 'Canada', 62, 170),
  ]);

  const [currentIndex, setCurrentIndex] = useState(7);

  useEffect(() => {
    if (updatedSwimmer != undefined) {
      const objIndex = swimmers.findIndex((obj) => obj.getId() === updatedSwimmer.id);
      if (objIndex !== -1) { 
        swimmers[objIndex] = updatedSwimmer;
        setSwimmers([...swimmers]);
      }
    }
  
    if (swimmerIndex != undefined) {
      const objIndex = swimmers.findIndex((obj) => obj.getId() === swimmerIndex);
      swimmers.splice(objIndex, 1);
      setSwimmers([...swimmers]);
    }

    if(newSwimmer != undefined) {
      newSwimmer.resetId(currentIndex);
      const newCurrentIndex = currentIndex + 1;
      setCurrentIndex(newCurrentIndex);
      setSwimmers([...swimmers, newSwimmer]);
    }

  }, [updatedSwimmer, swimmerIndex, newSwimmer]);
  

  const handleOnAddButton = () => {props.navigation.navigate("Add")}

  return (
      <ScrollView style={styles.container}>
          {swimmers.map((swimmer) => renderSwimmerCard({swimmer, props}))}
          <IconButton icon = "account-plus" iconColor="grey" size={50} onPress={handleOnAddButton} style={styles.centreIcon}/>
      </ScrollView>
  );
};

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  card: {
    marginBottom: 30, 
    backgroundColor: "#8BC6FC",
    fontSize:20,
  },
  image: {
    flex: 1,
    justifyContent: 'center',
  },
  iconButton: {
    backgroundColor: "grey",
  },
  centreIcon: {
    alignSelf: "center",
    marginBottom: 50,
  },
  button: {
    backgroundColor: "grey",
    alignSelf: "center",
    marginBottom: 50,
    color: "white",
    marginStart: 50,
    marginEnd: 50,
    marginTop: 50,
  },
  textInput: {
    backgroundColor: "#8BC6FC",
    marginBottom: 30, 
    height: 80,
    fontSize: 20,
    borderRadius: 8,
  }
});

export default SwimmerListScreen;
