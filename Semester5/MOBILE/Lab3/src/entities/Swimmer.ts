export default class Swimmer {
    private id : number;
    private fullname : string;
    private gender : string;
    private nationality : string;
    private weight : number;
    private height : number;
  
    constructor(id : number, fullname : string, gender : string, nationality : string, weight : number, height : number) {
      this.id = id;
      this.fullname = fullname;
      this.gender = gender;
      this.nationality = nationality;
      this.weight = weight;
      this.height = height;
    }
  
    getId() : number {
      return this.id;
    }
  
    getFullName() : string {
      return this.fullname;
    }
    
    getGender() : string {
      return this.gender;
    }
  
    getNationality() : string {
      return this.nationality;
    }
  
    getWeight() : number {
      return this.weight;
    }
  
    getHeight() : number {
      return this.height;
    }  

    resetId(newId: number) {
      this.id = newId;
    }
  }
    
    