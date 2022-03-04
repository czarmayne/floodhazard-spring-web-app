$(document).ready(function () {
    console.log("JS Ready");
    rainLocation();
    rainIntensity();
    floodForecast();
    changePageAndSize();
});

function changePageAndSize() {

    var url = window.location.href;
    console.log("CURRENT URL >>> "+ url);
    var pageSize = "pageSize=";
    if(url.indexOf('?date=') > -1) {
        console.log("date existing");
        url = url + "&" + pageSize;
    } else {
        console.log("date not existing");
        url = url + "?" + pageSize;
    }
    console.log("NEW URL >>> "+ url);
	$('#pageSizeSelect').change(function(evt) {
		window.location.replace(url+ this.value + "&page=1");
	});

	$('#pageSizeSelectIntensity').change(function(evt) {
        window.location.replace(url+ this.value + "&page=1");
    });

    $('#pageSizeSelectForecast').change(function(evt) {
        window.location.replace(url+ this.value + "&page=1");
    });
}

function rainLocation() {
    $('#locationTable').SetEditable({
        $addButton: $('#but_addL'),
        onEdit: function(arg) {
            var id = $(arg).attr('id');
            console.log("EDIT YEY "+ id);
            var $cols = $("#locationTable").find("#"+id);  //lee campos
            var data = {};
            data.id = $cols.find('td').eq(0).text();
            data.name = $cols.find('td').eq(1).text();
            data.details  = $cols.find('td').eq(2).text();
            data.levelOne  = $cols.find('td').eq(3).text();
            data.levelTwo  = $cols.find('td').eq(4).text();
            data.levelThree  = $cols.find('td').eq(5).text();
            data.levelFour  = $cols.find('td').eq(6).text();
            var json = JSON.stringify(data);

            console.log("data for location [] \n"+json)

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/admin/location", true);
            xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
            xhr.send(json);

//            $cols.find('td').each(function(i) {
//                 console.log("val each == "+$(this).text())
//                 console.log("\n ===="+i++);
//            });

        },   //Called after edition
        onDelete: function(arg) {
            console.log("last row id" + $('tr').last().attr('id'))
            console.log("DELETE YEY");
            var id = "/admin/location/"+$(arg).attr('id');
            console.log("delete for location >>> "+ id);
            var xhttp = new XMLHttpRequest();
            xhttp.open("DELETE", id, true);
            xhttp.send();
        }, //Called after deletion
        onAdd: function(arg) { console.log("ADD YEY"); }
     });

}

function rainIntensity() {
     $('#intensityTable').SetEditable({
         $addButton: $('#but_addI'),
         onEdit: function(arg) {
             var id = $(arg).attr('id');
             console.log("EDIT INTENSITY"+ id);
             var $cols = $("#intensityTable").find("#"+id);  //lee campos
             var data = {};
             data.date = $cols.find('td').eq(0).text();
             data.hour = $cols.find('td').eq(1).text();
             data.intensity  = $cols.find('td').eq(2).text();
             var json = JSON.stringify(data);

             console.log("data for rain intensity [] \n"+json)

             var xhr = new XMLHttpRequest();
             xhr.open("POST", "/admin/rainintensity", true);
             xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
             xhr.send(json);

    //            $cols.find('td').each(function(i) {
    //                 console.log("val each == "+$(this).text())
    //                 console.log("\n ===="+i++);
    //            });

         },   //Called after edition
         onDelete: function(arg) {
             console.log("last row id" + $('tr').last().attr('id'))
             var id = $(arg).attr('id');
             console.log("DELETE INTENSITY"+ id);
             var $cols = $("#intensityTable").find("#"+id);  //lee campos
             var data = {};
             data.date = $cols.find('td').eq(0).text();
             data.hour = $cols.find('td').eq(1).text();
             var json = JSON.stringify(data);
             console.log("delete for rain intensity >>> "+ json);
             var url = "/admin/rainintensity/"+data.date+"/"+data.hour;
             console.log("URL delete for rain intensity >>> "+ url);
             var xhttp = new XMLHttpRequest();
             xhttp.open("DELETE", url, true);
             xhttp.send();
         }
      });
}

function floodForecast() {
     $('#forecastTable').SetEditable({
         $addButton: $('#but_addFF'),
         onEdit: function(arg) {
             var id = $(arg).attr('id');
             console.log("EDIT FLOOD FORECAST"+ id);
             var $cols = $("#forecastTable").find("#"+id);  //lee campos
             var data = {};
             data.date = $cols.find('td').eq(0).text();
             data.hour = $cols.find('td').eq(1).text();
             data.intensity  = $cols.find('td').eq(2).text();
             data.sensitivityLevel  = $cols.find('td').eq(3).text();
             data.sensitivityDetail  = $cols.find('td').eq(4).text();
             var json = JSON.stringify(data);

             console.log("data for flood forecast [] \n"+json)

             var xhr = new XMLHttpRequest();
             xhr.open("POST", "/admin/forecast", true);
             xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
             xhr.send(json);

    //            $cols.find('td').each(function(i) {
    //                 console.log("val each == "+$(this).text())
    //                 console.log("\n ===="+i++);
    //            });

         },   //Called after edition
         onDelete: function(arg) {
             console.log("last row id" + $('tr').last().attr('id'))
             var id = $(arg).attr('id');
             console.log("DELETE FORECAST"+ id);
             var $cols = $("#intensityTable").find("#"+id);  //lee campos
             var data = {};
             data.date = $cols.find('td').eq(0).text();
             data.hour = $cols.find('td').eq(1).text();
             var json = JSON.stringify(data);
             console.log("delete for flood forecast >>> "+ json);
             var url = "/admin/forecast/"+data.date+"/"+data.hour;
             console.log("URL delete for flood forecast >>> "+ url);
             var xhttp = new XMLHttpRequest();
             xhttp.open("DELETE", url, true);
             xhttp.send();
         }
      });
}

function add_location_submit() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/admin/location",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });
}
