function validateForm() {
  let x = document.forms["myForm"];
  if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(\W|_)).{5,}$/.test(x[0].value))){
     alert("Password must contain at lest 1 lower case, 1 upper case, 1 number and 1 special symbol. Lenght of pssword must be bigger than 5 digits");
     return false;

  }else if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(\W|_)).{5,}$/.test(x[1].value))) {
     alert("Password must contain at lest 1 lower case, 1 upper case, 1 number and 1 special symbol. Lenght of pssword must be bigger than 5 digits");
     return false;
   }else if(x[2].value!= x[1].value){
     alert("Passwords are different!");
     return false;
   }

}
