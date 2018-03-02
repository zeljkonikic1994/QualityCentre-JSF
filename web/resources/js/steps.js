/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    

  var navListItems = $('div.setup-panel div a'),
          allWells = $('.setup-content'),
          allNextBtn = $('.nextBtn');

  allWells.hide();
 /* var $clicked = $($('btn btn-primary btn-circle').attr('href'));
  $clicked.show();
  $clicked.find('input:eq(0)').focus();*/

  navListItems.click(function (e) {
      e.preventDefault();
      var $target = $($(this).attr('href')),
              $item = $(this);

      if (!$item.hasClass('disabled')) {
          navListItems.removeClass('btn-primary').addClass('btn-default');
          $item.addClass('btn-primary');
          allWells.hide();
          $target.show();
          $target.find('input:eq(0)').focus();
      }
  });

  allNextBtn.click(function(){
      var curStep = $(this).closest(".setup-content"),
          curStepBtn = curStep.attr("id"),
          nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
          curInputs = curStep.find("input"),
          isValid = true;

      $(".form-group").removeClass("has-error");
      for(var i=0; i<curInputs.length; i++){
          if (curInputs[i].value===""){
              isValid = false;
              $(curInputs[i]).closest(".form-group").addClass("has-error");
          }
      }

      if (isValid)
          nextStepWizard.removeAttr('disabled').trigger('click');
  });

  $('div.setup-panel div a.btn-primary').trigger('click');
   //Initialize tooltips
    $('.nav-tabs > li a[title]').tooltip();
    
    //Wizard
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {

        var $target = $(e.target);
    
        if ($target.parent().hasClass('disabled')) {
            return false;
        }
    });

    $(".next-step").click(function (e) {
        var curStep = $(this).closest(".row"),
        curInputs = curStep.find("input"),
          isValid = true;

      $(".form-group").removeClass("has-error");
      if($(this).attr('id').indexOf("nextButtonAirport")<0) var $stepActive = $("#hrefFlStep");
      else if($(this).attr('id').indexOf("nextButtonFlight")<0) var $stepActive = $("#hrefApStep");
      for(var i=0; i<curInputs.length; i++){
          if(curInputs[i].name.indexOf("focus")<0){
              if (curInputs[i].value===""){
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
              }
          }
          if($(this).attr('id').indexOf("nextButtonAirport")<0){
              if($("#tabView\\:formFlight\\:airport1_label").text()===$("#tabView\\:formFlight\\:airport2_label").text()){
                isValid = false;
                  $(curInputs[i]).closest(".form-group").addClass("has-error");
                  break;
            }
            var selectedDate = PF('dd').getDate();
            var now = new Date();
            now.setHours(0,0,0,0);
            if (selectedDate < now) {
                isValid = false;
                $.growl.error({ message: "Date not valid!" });
                break;
            }
          }
          
      }

      if (isValid){
       
            $stepActive.next().removeClass('disabled');
            nextTab($stepActive);
        
        
      }
        

    });
    $(".prev-step").click(function (e) {
       
            $stepActive.next().removeClass('disabled');
             prevTab($stepActive);
        
        

    });
  
});

function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}




