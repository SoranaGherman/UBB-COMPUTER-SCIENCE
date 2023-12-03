import React, { useEffect, useState } from 'react';
import { StyleSheet, ScrollView, View, Text, Alert } from 'react-native';
import { Card, IconButton, Title } from 'react-native-paper';
import Swimmer from '../entities/Swimmer';
import * as SQLite from 'expo-sqlite';

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

  const [swimmers, setSwimmers] = useState<Swimmer[]>([]);

  const db = SQLite.openDatabase('swimmer.db');
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    db.transaction(tx => {
      tx.executeSql(
        'CREATE TABLE IF NOT EXISTS swimmer (id INTEGER PRIMARY KEY AUTOINCREMENT, fullname TEXT, gender TEXT, nationality TEXT, weight INTEGER, height INTEGER)',
        [],
        () => {
          tx.executeSql(
            'SELECT * FROM swimmer',
            [],
            (_, { rows }) => {
              const tempSwimmers = [];
              for (let i = 0; i < rows.length; i++) {
                tempSwimmers.push(new Swimmer(rows.item(i).id, rows.item(i).fullname, rows.item(i).gender, rows.item(i).nationality, rows.item(i).weight, rows.item(i).height));
              }
              setSwimmers(tempSwimmers);
              setIsLoading(false);
            },
            (txObj, error) => { 
              console.log('Error fetching swimmers: ', error);
              Alert.alert("Data Persistence Error", "Fetching data error!");
              return false; 
            }          
          );
        },
        (txObj, error) => { 
          console.log('Error fetching swimmers: ', error);
          Alert.alert("Data Persistence Error", "Creating table error!");
          return false; 
      }      
      );
    });
  }, []);

  useEffect(() => {
    if (updatedSwimmer != undefined) {
      db.transaction(tx => {
        tx.executeSql('UPDATE swimmer SET fullname=?, gender=?, nationality=?, weight=?, height=? WHERE id=?', [updatedSwimmer.fullname, updatedSwimmer.gender, 
                      updatedSwimmer.nationality, updatedSwimmer.weight, updatedSwimmer.height, updatedSwimmer.id],
                      (txObj, resultSet) => {
                          if (resultSet.rowsAffected > 0) {
                            const objIndex = swimmers.findIndex((obj) => obj.getId() === updatedSwimmer.id);
                            if (objIndex !== -1) { 
                              swimmers[objIndex] = updatedSwimmer;
                              setSwimmers([...swimmers]);
                            }
                          }
                      }, 
                      (txObj, error) => { 
                        console.log('Error fetching swimmers: ', error);
                        Alert.alert("Data Persistence Error", "Update Object error!");
                        return false; 
                      }     
                    );
      });
    }
  
    if (swimmerIndex != undefined) {
      db.transaction(tx => {
        tx.executeSql('DELETE FROM swimmer WHERE id=?', [swimmerIndex],
        (txObject, resultSet) => {
          if (resultSet.rowsAffected > 0) {
            const objIndex = swimmers.findIndex((obj) => obj.getId() === swimmerIndex);
            swimmers.splice(objIndex, 1);
            setSwimmers([...swimmers]);
          }
        }, 
        (txObj, error) => { 
          console.log('Error fetching swimmers: ', error);
          Alert.alert("Data Persistence Error", "Delete Object error!");
          return false; 
        }     
        );
      });
    }

    if(newSwimmer != undefined) {
      db.transaction(tx => {
        tx.executeSql('INSERT INTO swimmer (fullname, gender, nationality, weight, height) VALUES (?, ?, ?, ?, ?)', 
                      [ newSwimmer.getFullName(), newSwimmer.getGender(),
                        newSwimmer.getNationality(), newSwimmer.getWeight(),
                        newSwimmer.getHeight()],

                      (txObj, resultSet) => {
                        newSwimmer.resetId(resultSet.insertId || -1)
                        setSwimmers([...swimmers, newSwimmer]);
                      }, 
                      (txObj, error) => { 
                        console.log('Error fetching swimmers: ', error);
                        Alert.alert("Data Persistence Error", "Insert Object error!");
                        return false; 
                      }     
                      );
      });
    }
    
  }, [updatedSwimmer, swimmerIndex, newSwimmer]);
  

  const handleOnAddButton = () => {props.navigation.navigate("Add")}


  if (isLoading) {
    return (
      <View style={styles.container}>
        <Text>Loading info...</Text>
      </View>
    )
  }

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
