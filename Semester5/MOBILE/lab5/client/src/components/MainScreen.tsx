import React, { useEffect, useState } from 'react';
import { StyleSheet, ScrollView, View, Text, Alert } from 'react-native';
import { Card, IconButton, Title } from 'react-native-paper';
import Swimmer from '../entities/Swimmer';
import * as SQLite from 'expo-sqlite';

interface MainScreenProps {
  navigation : any;
  route : any;
}

const ip = `192.168.1.10:8000` // wifi from home
// const ip = `172.30.111.65:8000` // mateinfo
// const ip = `172.20.10.10:8000`

const url = `http://${ip}`

export const renderSwimmerCard = ({swimmer, props} : {swimmer:Swimmer, props : any}) => {

  const handleOnEditButton = () => {props.navigation.navigate("Edit", {paramKey: swimmer})}
  const handleOnDeleteButton = () => {props.navigation.navigate("Delete", {paramKey: swimmer})}

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
  // db.transaction(tx => {tx.executeSql('DELETE FROM swimmer')});

  const [isLoading, setIsLoading] = useState(true);

  const addSwimmer = (newSwimmer: Swimmer) => {

    const requestBody = {
      fullname: newSwimmer.getFullName(),
      gender: newSwimmer.getGender(),
      nationality: newSwimmer.getNationality(),
      weight: newSwimmer.getWeight(),
      height: newSwimmer.getHeight(),
    };
  
    fetch(`${url}/swimmers/`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody),
    })
      .then((response) => {return response.json()})
      .then((data) => {
        if (data.id) {
          // Update the swimmer's ID with the one received from the server
          newSwimmer.resetId(data.id);
  
          db.transaction(tx => {
            tx.executeSql(
              'INSERT INTO swimmer (id, fullname, gender, nationality, weight, height) VALUES (?, ?, ?, ?, ?, ?)', 
              [newSwimmer.getId(), newSwimmer.getFullName(), newSwimmer.getGender(),
                newSwimmer.getNationality(), newSwimmer.getWeight(), newSwimmer.getHeight()],
              (txObj, resultSet) => {
                setSwimmers(currentSwimmers => [...currentSwimmers, newSwimmer]);
              }, 
              (txObj, error) => { 
                console.log('Error inserting swimmers: ', error);
                Alert.alert("Data Persistence Error", "Insert Object error!");
                return false; 
              }     
              );
          });
        } else {
          console.error('Failed to add swimmer on the server');
          Alert.alert('Server Error', 'Failed to add swimmer on the server');
        }
      })
      .catch((error) => {
        if (error.toString().includes("TypeError: Network request failed") || error.toString().includes("WebSocket connection closed ")) {
          console.log('Inserting from server failed, doing from local DB');

          db.transaction(tx => {
            tx.executeSql(
              'SELECT MIN(id) AS minId FROM swimmer',
              [],
              (txObj, resultSet) => {
                const minId = resultSet.rows.item(0).minId;
                console.log('Minimum ID:', minId);

                const minimumId = Math.min(minId, 0);
                console.log('Minimum ID (adjusted):', minimumId);

                const currentId = minimumId - 1;
                console.log('Current ID:', currentId);

                newSwimmer.resetId(currentId);

                db.transaction(tx => {
                  tx.executeSql(
                    'INSERT INTO swimmer (id, fullname, gender, nationality, weight, height, flag) VALUES (?, ?, ?, ?, ?, ?, ?)', 
                    [currentId, newSwimmer.getFullName(), newSwimmer.getGender(),
                      newSwimmer.getNationality(), newSwimmer.getWeight(), newSwimmer.getHeight(), 1],
                    (txObj, resultSet) => {
                      setSwimmers(currentSwimmers => [...currentSwimmers, newSwimmer]);
                    }, 
                    (txObj, error) => { 
                      console.log('Error inserting swimmers: ', error);
                      Alert.alert("Data Persistence Error", "Insert Object error!");
                      return false; 
                    }     
                    );
                });

              },
              (txObj, error) => {
                console.log('Error selecting minimum ID:', error);
                return false;
              }
            );
          });
        }
      });
  };

  const updateSwimmer = (updatedSwimmer: Swimmer) => {
    const requestBody = {
      id: updatedSwimmer.getId(),
      fullname: updatedSwimmer.getFullName(),
      gender: updatedSwimmer.getGender(),
      nationality: updatedSwimmer.getNationality(),
      weight: updatedSwimmer.getWeight(),
      height: updatedSwimmer.getHeight(),
    };
  
    fetch(`${url}/swimmers/${updatedSwimmer.getId()}/`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.id) {
          db.transaction(tx => {
            tx.executeSql('UPDATE swimmer SET fullname=?, gender=?, nationality=?, weight=?, height=? WHERE id=?', [updatedSwimmer.getFullName(), updatedSwimmer.getGender(), 
                          updatedSwimmer.getNationality(), updatedSwimmer.getWeight(), updatedSwimmer.getHeight(), updatedSwimmer.getId()],
                          (txObj, resultSet) => {
                              if (resultSet.rowsAffected > 0) {
                                setSwimmers(currentSwimmers => {
                                  const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === updatedSwimmer.getId());
                                  if (objIndex != -1) {
                                    currentSwimmers[objIndex] = updatedSwimmer;
                                  }
                                  return [...currentSwimmers];
                                  });
                              }
                          }, 
                          (txObj, error) => { 
                            console.log('Error fetching swimmers: ', error);
                            Alert.alert("Data Persistence Error", "Update Object error!");
                            return false; 
                          }     
                        );
          });
        } else {
          console.error('Failed to update swimmer on the server');
          Alert.alert('Server Error', 'Failed to update swimmer on the server');
        }
      })
      .catch((error) => {
        if (error.toString().includes("TypeError: Network request failed") || error.toString().includes("WebSocket connection closed ")) {
          console.log('Updating from server failed, fetching from local DB');
          db.transaction(tx => {

            const whatFlag = (updatedSwimmer.getId() < 0) ? 1 : 2;
            console.log(updatedSwimmer.getId());
            console.log("update flag?", whatFlag);
          
            tx.executeSql('UPDATE swimmer SET fullname=?, gender=?, nationality=?, weight=?, height=?, flag=? WHERE id=?', [updatedSwimmer.getFullName(), updatedSwimmer.getGender(), 
              updatedSwimmer.getNationality(), updatedSwimmer.getWeight(), updatedSwimmer.getHeight(), whatFlag, updatedSwimmer.getId()],
              (txObj, resultSet) => {
                if (resultSet.rowsAffected > 0) {
                  setSwimmers(currentSwimmers => {
                    const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === updatedSwimmer.getId());
                    if (objIndex != -1) {
                      currentSwimmers[objIndex] = updatedSwimmer;
                    }
                    return [...currentSwimmers];
                    });
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
      });
  };

  const deleteSwimmer = (swimmerId: number) => {
    fetch(`${url}/swimmers/${swimmerId}/`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.status === 204) {
          db.transaction(tx => {
            tx.executeSql('DELETE FROM swimmer WHERE id=?', [swimmerIndex],
            (txObject, resultSet) => {
              if (resultSet.rowsAffected > 0) {
                setSwimmers(currentSwimmers => {
                  const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === swimmerId);
                  currentSwimmers.splice(objIndex, 1);
                  return [...currentSwimmers];
                });
              }
            }, 
            (txObj, error) => { 
              console.log('Error deleting swimmers: ', error);
              Alert.alert("Data Persistence Error", "Delete Object error!");
              return false; 
            }     
            );
          });
        } else {
          console.error('Failed to delete swimmer on the server');
          Alert.alert('Server Error', 'Failed to delete swimmer on the server');
        }
      })
      .catch((error) => {
        if (error.toString().includes("TypeError: Network request failed") || error.toString().includes("WebSocket connection closed ")) {
          console.log('Deleting from server failed, doing from local DB');

          console.log("DELETE SWIMMER ID: ", swimmerId);

          if (swimmerId < 0) {
            db.transaction(tx => {tx.executeSql('DELETE FROM swimmer WHERE id=?', [swimmerId],
            (txObj, resultSet) => {
              if (resultSet.rowsAffected > 0) {
                setSwimmers(currentSwimmers => {
                  const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === swimmerId);
                  currentSwimmers.splice(objIndex, 1);
                  return [...currentSwimmers];
                });
              }
            })});            
          }

          else {
            db.transaction(tx => {tx.executeSql('UPDATE swimmer SET flag = 3 WHERE id=?', [swimmerId],
              (txObj, resultSet) => {
                if (resultSet.rowsAffected > 0) {
                  setSwimmers(currentSwimmers => {
                    const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === swimmerId);
                    currentSwimmers.splice(objIndex, 1);
                    return [...currentSwimmers];
                  });
                }
              })});
            }
          }
      });
  };
  
  //////// web sockets //////

  const addSwimmerWS = (newSwimmer: Swimmer) => {
    db.transaction(tx => {
      tx.executeSql('INSERT INTO swimmer (id, fullname, gender, nationality, weight, height) VALUES (?, ?, ?, ?, ?, ?)', 
                    [ newSwimmer.getId(), newSwimmer.getFullName(), newSwimmer.getGender(),
                      newSwimmer.getNationality(), newSwimmer.getWeight(),
                      newSwimmer.getHeight()],

                    (txObj, resultSet) => {
                      setSwimmers(currentSwimmers => [...currentSwimmers, newSwimmer]);
                    }, 
                    (txObj, error) => { 
                      console.log('Error inserting swimmers: ', error);
                      Alert.alert("Data Persistence Error", "Insert Object error!");
                      return false; 
                    }     
                    );
    });
  }

  const updateSwimmerWS = (updatedSwimmer : Swimmer) => {
    db.transaction(tx => {
      tx.executeSql('UPDATE swimmer SET fullname=?, gender=?, nationality=?, weight=?, height=? WHERE id=?', [updatedSwimmer.getFullName(), updatedSwimmer.getGender(), 
                    updatedSwimmer.getNationality(), updatedSwimmer.getWeight(), updatedSwimmer.getHeight(), updatedSwimmer.getId()],
                    (txObj, resultSet) => {
                        if (resultSet.rowsAffected > 0) {
                          setSwimmers(currentSwimmers => {
                            const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === updatedSwimmer.getId());
                            if (objIndex != -1) {
                              currentSwimmers[objIndex] = updatedSwimmer;
                            }
                            return [...currentSwimmers];
                            });
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

  const deleteSwimmerWS = (deletedSwimmerId : any) => {
    db.transaction(tx => {
      tx.executeSql('DELETE FROM swimmer WHERE id=?', [deletedSwimmerId],
      (txObject, resultSet) => {
        if (resultSet.rowsAffected > 0) {
          setSwimmers(currentSwimmers => {
            const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === deletedSwimmerId);
            currentSwimmers.splice(objIndex, 1);
            return [...currentSwimmers];
          });
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

  const handleWebSocketMessage = (message : any) => {
    switch (message.action) {
      case 'create':
        const mySwimmer = new Swimmer(message.swimmer.id, message.swimmer.fullname, message.swimmer.gender, message.swimmer.nationality,
                                message.swimmer.weight, message.swimmer.height)
        addSwimmerWS(mySwimmer);
        break;
      case 'update':
        const mySwimmerUpdate = new Swimmer(message.swimmer.id, message.swimmer.fullname, message.swimmer.gender, message.swimmer.nationality,
          message.swimmer.weight, message.swimmer.height)

        updateSwimmerWS(mySwimmerUpdate);
        break;
      case 'delete':
        deleteSwimmerWS(message.swimmer.id); 
        break;
      default:
        console.log('Received unknown message type', message.type);
    }
  };

  /// fetch from local db the retrieval of the list if server is down ////

  const fetchSwimmersFromDB = () => {
    db.transaction(tx => {
      tx.executeSql(
        'SELECT * FROM swimmer WHERE flag != 3',
        [],
        (_, { rows }) => {
          const tempSwimmers = [];
          for (let i = 0; i < rows.length; i++) {
            tempSwimmers.push(new Swimmer(rows.item(i).id, rows.item(i).fullname, rows.item(i).gender, rows.item(i).nationality, rows.item(i).weight, rows.item(i).height));
          }
          setSwimmers([...tempSwimmers]); // Directly set the swimmers from DB
          setIsLoading(false); // Update loading state
        },
        (txObj, error) => { 
          console.log('Error fetching swimmers from DB: ', error);
          Alert.alert("Data Persistence Error", "Fetching data error!");
          return false; 
        }
      );
    });
  };

  // sync with server ////

  const syncSwimmersWithServer = () => {
    db.transaction(tx => {
      tx.executeSql(
        'SELECT * FROM SWIMMER WHERE flag = 1 OR flag = 2 OR flag = 3',
        [],
        (_, { rows }) => {
          const swimmersForServer = [];
          for (let i = 0; i < rows.length; i++) {
            swimmersForServer.push({
              swimmer: new Swimmer(rows.item(i).id, rows.item(i).fullname, rows.item(i).gender, rows.item(i).nationality, rows.item(i).weight, rows.item(i).height),
              flag: rows.item(i).flag
            });
          }
  
          swimmersForServer.forEach(swimmerWithFlag => {
            const swimmer = swimmerWithFlag.swimmer;
            const flag = swimmerWithFlag.flag;

            switch (flag) {
              //POST
              case 1: 
              const requestBodyPost = {
                fullname: swimmer.getFullName(),
                gender: swimmer.getGender(),
                nationality: swimmer.getNationality(),
                weight: swimmer.getWeight(),
                height: swimmer.getHeight(),
              };

              fetch(`${url}/swimmers/`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestBodyPost),
              })
                .then(response => response.json())
                .then(data => {
                  const addedSwimmer = swimmerWithFlag.swimmer;
                  if (data.id) {
                    const newServerId = data.id;
                    const oldLocalId = addedSwimmer.getId();

                    // Update local DB
                    db.transaction(tx => {
                      tx.executeSql(
                        'UPDATE SWIMMER SET id = ?, flag = 0 WHERE id = ?',
                        [newServerId, oldLocalId],
                        (_, resultSet) => {
                          if (resultSet.rowsAffected > 0) {
                            console.log(`Swimmer ID updated from ${oldLocalId} to ${newServerId}`);
                            setSwimmers(currentSwimmers => {
                              const objIndex = currentSwimmers.findIndex((obj) => obj.getId() === oldLocalId);
                              if (objIndex != -1) {
                                currentSwimmers[objIndex].resetId(newServerId);
                              }
                              return [...currentSwimmers];
                              });
                            
                          } else {
                            console.log(`Failed to update swimmer ID in local DB`);
                          }
                        },
                        (_, error) => {
                          console.log('Error updating swimmer ID:', error);
                          return false;
                        }
                      );
                    });
                  } else {
                    console.log('Failed to add swimmer on the server');
                  }
                })
                .catch(error => {
                  console.log('Error making POST request:', error);
                });
              break;

              
              // PUT  
              case 2: 
                const requestBody = {
                  id: swimmer.getId(),
                  fullname: swimmer.getFullName(),
                  gender: swimmer.getGender(),
                  nationality: swimmer.getNationality(),
                  weight: swimmer.getWeight(),
                  height: swimmer.getHeight(),
                };

                fetch(`${url}/swimmers/${swimmer.getId()}/`, {
                  method: 'PUT',
                  headers: {
                    'Content-Type': 'application/json',
                  },
                  body: JSON.stringify(requestBody),
                })
                  .then((response) => response.json())
                  .then((data) => {
                    const updatedSwimmer = swimmerWithFlag.swimmer;
                    db.transaction(tx => {
                      tx.executeSql(
                        'UPDATE SWIMMER SET flag = 0 WHERE id = ?',
                        [updatedSwimmer.getId()],
                        (_, { rowsAffected }) => {
                          if (rowsAffected > 0) {
                            console.log('Flag updated successfully');
                          } else {
                            console.log('Failed to update flag');
                          }
                        },
                        (_, error) => {
                          console.log('Error updating flag:', error);
                          return false;
                        }
                      );
                    });
                  })
                  .catch((error) => {
                    console.log('Error making PUT request:', error);
                  });
                break;


              // DELETE  
              case 3: 
              fetch(`${url}/swimmers/${swimmer.getId()}/`, {
                method: 'DELETE'
              })
              .then((response) => {
                if (response.status === 204) {
                  const deletedSwimmer = swimmerWithFlag.swimmer;
                  console.log(deletedSwimmer.getId());
                  db.transaction(tx => {
                    tx.executeSql(
                      'DELETE FROM swimmer WHERE id = ?',
                      [deletedSwimmer.getId()],
                      (_, resultSet) => {
                        if (resultSet.rowsAffected > 0) {
                          console.log(`Swimmer with ID ${deletedSwimmer.getId()} deleted from local DB`);
                        } else {
                          console.log(`No swimmer found with ID ${deletedSwimmer.getId()} in local DB`);
                        }
                      },
                      (txObj, error) => {
                        console.log('Error deleting swimmer from local DB:', error);
                        return false;
                      }
                    );});
                  } else {
                    throw new Error('Server responded with an error!');
                  }
              })
              .catch((error) => {
                console.log('Error making DELETE request or deleting from local DB:', error);
              });
              break;
            
              default:
                break;
                }
          });
        },
        (txObj, error) => { console.log('Error fetching swimmers for sync:', error); return false;}
      );
    });
  }


  ///// useEffects ////////

  useEffect(() => {
    db.transaction(tx => {
      tx.executeSql(
        'CREATE TABLE IF NOT EXISTS swimmer (id INTEGER PRIMARY KEY, fullname TEXT, gender TEXT, nationality TEXT, weight INTEGER, height INTEGER, flag INTEGER DEFAULT 0)',
        [],
        () => {
          fetch(`${url}/swimmers/`, {method: 'GET'})
            .then((response) => response.json())
            .then((data) => {
              if (data.length == 0) {
                setIsLoading(false);
              }

              if (data) {
                data.forEach((swimmer: any) => {
                  // update the in memory list with data from server
                  const updatedSwimmer = new Swimmer(
                    swimmer.id,
                    swimmer.fullname,
                    swimmer.gender,
                    swimmer.nationality,
                    swimmer.weight,
                    swimmer.height
                  );
                  setSwimmers((prevSwimmers) => [...prevSwimmers, updatedSwimmer]);
                  setIsLoading(false);
                });
              }
            })
            .catch((error) => {
              if (error.toString().includes("TypeError: Network request failed")) {
                console.log('Fetching from server failed, fetching from local DB');
                fetchSwimmersFromDB(); 
              }
            });         
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
    let ws : any;
  
    const connectWebSocket = () => {
      ws = new WebSocket(`ws://${ip}/ws/swimmers/`);
  
      ws.onopen = () => {
        console.log('WebSocket connection opened');
        syncSwimmersWithServer();
      };
  
      ws.onmessage = (e : any) => {
        const message = JSON.parse(e.data);
        handleWebSocketMessage(message);
      };
  
      ws.onerror = (e : any) => {
        console.error('WebSocket encountered an error');
      };
  
      ws.onclose = (e : any) => {
        console.log('WebSocket connection closed', e.code, e.reason);
        // Try to reconnect after a short delay
        setTimeout(() => connectWebSocket(), 3000);
      };
    };
  
    connectWebSocket();
  
    return () => {
      ws.close();
    };
  }, []);
 

  useEffect(() => {
    if (updatedSwimmer != undefined) {
      updateSwimmer(updatedSwimmer);
    }
  
    if (swimmerIndex != undefined) {
      deleteSwimmer(swimmerIndex);
    }

    if(newSwimmer != undefined) {
      addSwimmer(newSwimmer);
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
