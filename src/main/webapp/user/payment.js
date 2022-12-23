

function keyCard(e){
  var keynum;

  if(window.event) { // IE
    keynum = e.keyCode;
  }

  console.log(keynum);
  var input = document.getElementById("card-number");
  if(keynum!=8&& keynum>47&&keynum<58){
  var str = input.value.replace(/\s/g, '');
    if (str.length<16) {
    input.value+=String.fromCharCode(event.which);
    str = input.value.replace(/\s/g, '');
          if (str.length % 4 == 0&& str.length!=16) {
              input.value += " ";
          }

      }
      if(str.length==17){
        input.value = input.value.slice(0,-1);
      }
  }else{
    if(keynum==8){
      if(input.value.slice(-1)==" "){
        input.value = input.value.slice(0,-1);
      }
      input.value = input.value.slice(0,-1);
    }

  }
  if(input.value.length>0){
    document.getElementById("card-label").className = 'input-label';
  }else{
    document.getElementById("card-label").className ='';
  }
}


function keyDate(e){
  var keynum;

  if(window.event) { // IE
    keynum = e.keyCode;
  }

  console.log(keynum);
  var input = document.getElementById("card-date");
  if(keynum!=8&& keynum>47&&keynum<58){
    var str = input.value.replace('/', '');
    if (str.length==0&&keynum>47&&keynum<50) {
    input.value+=String.fromCharCode(event.which);


  }else if(str.length==1&&keynum>48&&keynum<51){
      input.value+=String.fromCharCode(event.which);
      str = input.value.replace('/', '');
        if (str.length % 2 == 0&& str.length!=4) {
            input.value += "/";
        }
      }
      else if (str.length<4&&str.length>=2) {
      input.value+=String.fromCharCode(event.which);

        }
        if(str.length==5){
          input.value = input.value.slice(0,-1);
        }
    }else{
      if(keynum==8){
      if(input.value.slice(-1)=="/"){
        input.value = input.value.slice(0,-1);
      }
        input.value = input.value.slice(0,-1);
      }
    }
    if(input.value.length>0){
      document.getElementById("date-label").className = 'input-label';
    }else{
      document.getElementById("date-label").className ='';
    }
}


function validateForm() {
  let x = document.forms["myForm"];
  if (!(/^([0-9]){1,3}$/.test(x[2].value))) {
    alert("CVV must contain only number");
    return false;
  }else if(x[0].value.length!=19){
    console.log(x[0].value.lenght);
    alert("You have not entered card number");
    return false;
  }else if(x[1].value.length!=5){
    alert("You have not entered card date");
    return false;
  }


}
