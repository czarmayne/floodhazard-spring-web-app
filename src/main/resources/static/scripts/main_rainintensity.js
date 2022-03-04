$(document).ready(function () {
    console.log("JS Ready For RAIN INTENSITY");
    rainIntensity();
    changePageAndSize();
});

function changePageAndSize() {
    var url = window.location.href;
    console.log("CURRENT URL >>> "+ url);
    var pageSize = "pageSize=";

    if (url.indexOf('pageSize') > -1) {
        console.log("existing pagination \n" + window.location.search)
        url = url.replace(window.location.search, '');
        console.log("after replacing >> \n" + url)
    }

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


