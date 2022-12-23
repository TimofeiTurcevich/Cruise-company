var lastAdultChange = 1;
var luxPrice;
var standardPrice;

function adultChange(){
  if(luxPrice==null){
    luxPrice = document.getElementById("luxPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','');
  }
  if(standardPrice==null){
    standardPrice = document.getElementById("standardPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','');
  }
  var input = document.getElementById("child");
  var input2 = document.getElementById("adult");
  document.getElementById("standardAdult").value = input2.value;
  document.getElementById("luxAdult").value = input2.value;
  if(input2.value>lastAdultChange){
    lastAdultChange = input2.value;
    input.setAttribute("max", input.getAttribute("max")-1);
    document.getElementById("luxPrice").innerHTML = parseInt(document.getElementById("luxPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) + parseInt(luxPrice) + '$';
    document.getElementById("standardPrice").innerHTML = parseInt(document.getElementById("standardPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) + parseInt(standardPrice) + '$';
  }else{
    lastAdultChange = input2.value;
    input.setAttribute("max", parseInt(input.getAttribute("max"))+1);
    document.getElementById("luxPrice").innerHTML = parseInt(document.getElementById("luxPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) - parseInt(luxPrice) + '$';
    document.getElementById("standardPrice").innerHTML = parseInt(document.getElementById("standardPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) - parseInt(standardPrice) + '$';
    console.log(input.getAttribute("max"));
  }
}
var lastChildChange = 0;

function childChange(){
  if(luxPrice==null){
    luxPrice = document.getElementById("luxPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','');
  }
  if(standardPrice==null){
    standardPrice = document.getElementById("standardPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','');
  }
  var input = document.getElementById("adult");
  var input2 = document.getElementById("child");
  document.getElementById("standardChild").value = input2.value;
  document.getElementById("luxChild").value = input2.value;
  if(input2.value>lastChildChange){
    lastChildChange = input2.value;
    document.getElementById("luxPrice").innerHTML = parseInt(document.getElementById("luxPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) + parseInt(luxPrice*70/100) + '$';
    document.getElementById("standardPrice").innerHTML = parseInt(document.getElementById("standardPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) + parseInt(standardPrice*70/100) + '$';
    input.setAttribute("max", input.getAttribute("max")-1);
  }else{
    lastChildChange = input2.value;
    document.getElementById("luxPrice").innerHTML = parseInt(document.getElementById("luxPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) - parseInt(luxPrice*70/100) + '$';
    document.getElementById("standardPrice").innerHTML = parseInt(document.getElementById("standardPrice").innerHTML.replaceAll('\n','').replaceAll(' ','').replaceAll('$','')) - parseInt(standardPrice*70/100) + '$';
    input.setAttribute("max", parseInt(input.getAttribute("max"))+1);
    console.log(input.getAttribute("max"));
  }
}
