<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Display month &amp; year menus</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script>
  $( function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
      changeMonth : true,
		changeYear : true,
		yearRange : '1970:2030',
		dateFormat : 'dd/mm/yy',
		endDate : '-18y',
			maxDate:0 
    });
  } );

  function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
	  return [true];
	  }
  }
  
  $( function() {
	  $( "#udate5" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'2022:2023',
		  dateFormat:'dd/mm/yy',

// Disable for Sunday
		  beforeShowDay : disableSunday,		  
// Disable for back date
		  minDate : 0   
	  });
  } );


  $(function () {
	    $("#datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',
	        changeMonth: true,
	        changeYear: true,

	        // Show past 40 years and current year up to today (2025)
	        yearRange: "1980:+0",  

	        // Allow selecting up to today's date
	        maxDate: 0
	    });
	});
	  </script>
</head>
<body>
 
<!-- <p>Date: <input type="text" id="datepicker"></p> -->
 
 
</body>
</html>