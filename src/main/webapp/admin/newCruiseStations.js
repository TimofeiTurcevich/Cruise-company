function validateForm() {
  let x = document.forms["myForm"];
  var date;
  for(var i = 6; i<x.elements.length-2;i++){
    if(i%4==0){
      i+=2;
    }
    if(i!=6&&x.elements[i-2].value == x.elements[i-4].value){
      alert("One cannot be twice in a row");
      return false;
    }
    if(x.elements[i-2].value=='0'){
      alert("You need to choose station");
      return false;
    }
    if(i%2==1&&i!=7){
      if(new Date(x.elements[i].value)<date){
        alert("Arival date cant be later than departure");
        return false;
      }
    }
    if(i%2==0&&i!=6){
      if(new Date(x.elements[i].value)<date){
        alert("Departure date cant be earlier than arrival at last station");
        return false;
      }
    }
    date = new Date(x.elements[i].value);
  }
}
