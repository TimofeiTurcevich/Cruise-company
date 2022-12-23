function validateForm() {
  let x = document.forms["myForm"];
    var birth = new Date(x[4].value);
    var today = new Date().toJSON().slice(1,10);
    var myAge = ~~((Date.now(today)-birth) / (31557600000));
  if (!(/^([a-z]|[A-Z]|[0-9]){1,}?[@]{1}?[a-z]{1,}?[.]{1}?[a-z]{2,3}$/.test(x[0].value))) {
    alert("Email was written incorret");
    return false;
  }else if (!(/^[A-Z]{1}?[a-z]{1,}$/.test(x[1].value))||(/^(?=.*\d).{1,}$/.test(x[1].value))) {
    alert("Name must start with upper case and contain only letters");
     return false;
   }if (!(/^[A-Z]{1}?[a-z]{1,}$/.test(x[2].value))||(/^(?=.*\d).{1,}$/.test(x[2].value))) {
     alert("Name must start with upper case and contain only letters");
     return false;
   }else if(!(/^[\+]?[0-9]{1,3}?[0-9]{3}?[0-9]{4,6}$/im.test(x[3].value))){
     alert("phone must start with '+' and contain only numbers. At least must be 8-12 digits")
     return false;
   }else if(myAge<18){
     alert("User must be 18 years old and elder");
     return false;
   }else if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(\W|_)).{5,}$/.test(x[5].value))) {
     alert("Password must contain at lest 1 lower case, 1 upper case, 1 number and 1 special symbol. Lenght of pssword must be bigger than 5 digits");
     return false;
   }else if(x[5].value!= x[6].value){
     alert("Passwords are different!");
     return false;
   }

}
