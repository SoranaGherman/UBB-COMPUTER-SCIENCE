import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import AppNavigator from './src/navigator';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <AppNavigator/>
  );
}
