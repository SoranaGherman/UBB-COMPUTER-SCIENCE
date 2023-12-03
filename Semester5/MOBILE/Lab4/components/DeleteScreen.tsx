import { View, Text } from "react-native";
import { Button } from "react-native-paper";
import { styles } from "./MainScreen";

interface DeleteScreenProps {
    navigation : any;
    route : any;
  }

const DeleteSwimmer = (props : DeleteScreenProps) => {

    const selectedSwimmer = props.route.params.paramKey; 

    const handleCancel = () => {props.navigation.navigate("Swimmers List")}
    const handleDelete = () => {props.navigation.navigate("Swimmers List", {swimmerIndex : selectedSwimmer.id})}

    return (
      <View style={styles.container}>
        <Text>Are you sure you want to delete this swimmer? This action will be permanent!</Text>
        <View style={{ flexDirection: 'row', justifyContent: 'space-between' }}>
          <Button onPress={handleCancel} labelStyle={{color: "white"}} style={styles.button}>CANCEL</Button>
          <Button onPress={handleDelete} labelStyle={{color: "white"}} style={styles.button}>DELETE</Button>
        </View>
    </View>
    );
}

export default DeleteSwimmer;