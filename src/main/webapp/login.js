function validateForm() {
    let x = document.forms["myForm"];
   if (!(/^([a-z]|[A-Z]|[0-9]){1,}?[@]{1}?[a-z]{1,}?[.]{1}?[a-z]{2,3}$/.test(x[0].value))) {
     alert("Email was written incorret");
     return false;
   }else if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(\W|_)).{5,}$/.test(x[1].value))) {
     alert("Password must contain at lest 1 lower case, 1 upper case, 1 number and 1 special symbol. Lenght of pssword must be bigger than 5 digits");
     return false;
   }

}
