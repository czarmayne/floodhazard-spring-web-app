$(document).ready(function () {
    console.log("JS Ready For PRECIPITATION CONDITION");
    rainLocation();
});

function rainLocation() {
    $('#precipitationConditionTable').SetEditable({
        $addButton: $('#but_addPrecipitationCondition'),
        onEdit: function(arg) {
            var id = $(arg).attr('id');
            console.log("EDIT YEY "+ id);
            var $cols = $("#precipitationConditionTable").find("#"+id);  //lee campos
            var data = {};
            data.id = $cols.find('td').eq(0).text();
            data.sensitivityLevel = $cols.find('td').eq(1).text();
            data.sensitivityDetail  = $cols.find('td').eq(2).text();
            var json = JSON.stringify(data);

            console.log("data for precipitationCondition [] \n"+json)

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/admin/sensitivityCondition", true);
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
            var id = "/admin/sensitivityCondition/"+$(arg).attr('id');
            console.log("delete for sensitivityCondition >>> "+ id);
            var xhttp = new XMLHttpRequest();
            xhttp.open("DELETE", id, true);
            xhttp.send();
        }, //Called after deletion
        onAdd: function(arg) { console.log("ADD YEY"); }
     });

}
