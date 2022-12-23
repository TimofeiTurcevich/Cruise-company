function validateForm() {
  let x = document.forms["myForm"];
    var birth = new Date(x[3].value);
    var today = new Date().toJSON().slice(1,10);
    var myAge = ~~((Date.now(today)-birth) / (31557600000));
  if (!(/^([a-z]|[A-Z]|[0-9]){1,}?[@]{1}?[a-z]{1,}?[.]{1}?[a-z]{2,3}$/.test(x[2].value))) {
    alert("Email was written incorret");
    return false;
  }else if (!(/^[A-Z]{1}?[a-z]{1,}$/.test(x[1].value))||(/^(?=.*\d).{1,}$/.test(x[0].value))) {
    alert("Name must start with upper case and contain only letters");
     return false;
   }if (!(/^[A-Z]{1}?[a-z]{1,}$/.test(x[2].value))||(/^(?=.*\d).{1,}$/.test(x[1].value))) {
     alert("Name must start with upper case and contain only letters");
     return false;
   }else if(!(/^[\+]?[0-9]{1,3}?[0-9]{3}?[0-9]{4,6}$/im.test(x[4].value))){
     alert("phone must start with '+' and contain only numbers. At least must be 8-12 digits")
     return false;
   }else if(myAge<18){
     alert("User must be 18 years old and elder");
     return false;
   }

}
