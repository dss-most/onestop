/**
 * 
 */
var AppRouter = Backbone.Router.extend({
	initialize : function(options) {
		
		if(options.formView != null) {
			this.formView = options.formView;	
		} 
		if(options.searchView != null) {
			this.searchView = options.searchView;
		}
		if(options.tableResultView != null) {
			this.tableResultView = options.tableResultView;
		}
		
	},
	routes: {
        "newRFQ" : "newForm",
        "showRFQ/:id" : "showForm",
        "search" : "search",
        "OrganizationNetwork/:id" : "editForm",
        "*actions": "defaultRoute" // Backbone will try match the route above first
    },
    
    defaultRoute: function(action) {
    	this.search();
    	
    },
    searchWithModelAndPage: function(model, pageNum) {
    	// now table result
    	this.tableResultView.renderWithSearchModel(model, pageNum);
    },
    search: function() {
    	// set breadcrumb
    	//this.$breadcrubmEl.html(this.defaultBreadCrumb());

    	// show search
    	this.searchView.render();
    	// no form
    	this.formView.$el.empty();
    	// no table result
    	this.tableResultView.render();
    	
    },
    
    showForm: function(id) {
    	this.tableResultView.$el.empty();
    	this.searchView.$el.empty();
    	
    	this.formView.showForm(id);
    },
    
    newForm: function() {
    	this.tableResultView.$el.empty();
    	this.searchView.$el.empty();
    	//this.$breadcrubmEl.html(this.newFormBreadCrumb());
    	
    	this.formView.newForm();
    },
    
    editForm: function(id){
    	this.searchView.$el.empty();
    	this.tableResultView.$el.empty();
    	var json={};
    	json.companyId=id;
    	this.$breadcrubmEl.html(this.editFormBreadCrumb(json));
    	
    	this.formView.editForm(id);
    }
    
    
});


var SearchView = Backbone.View.extend({
    initialize: function(options){
    	this.searchViewTemplate = Handlebars.compile($("#searchViewTemplate").html());
    	
    	this.searchModel = new App.Models.Rfq(); 
    },
    
    events: {
    	"change .formSlt": "onChangeFormSlt",
    	"change .formTxt" : "onChangeFormTxt",
    	
    	
    	"click #newFormBtn" : "onClicknewFormBtn",
    	"click #searchBtn" : "onClickSearchBtn",
    	"click #clearFormBtn" : "onClickClearFormBtn",
    	"submit #searchForm" : "onSubmitSearchForm"
    		
    },
   
    onClickClearFormBtn: function(e) {
    	this.searchModel = new smt.Model.OrganizationNetwork();
    	
    	appRouter.search();
    },
    onSubmitSearchForm: function(e) {
    	this.onClickSearchBtn(e);
    	return false;
    },
    onChangeFormTxt: function(e) {
    	var value = $(e.currentTarget).val();
		var field=$(e.currentTarget).attr('data-field'); 
		this.searchModel.set(field, value);
    },
    onClickSearchBtn:function(e) {
    	appRouter.searchWithModelAndPage(this.searchModel, 1);
    },
    onClicknewFormBtn : function(e) {
    	// we can simply navigate to newForm
    	appRouter.navigate("newOrgnazationNetwork", {trigger: true});
    	
    },
    onChangeFormSlt: function(e) {
    	//get healthZone val
    	var id=$(e.currentTarget).val();
    	var field=$(e.currentTarget).attr('data-field'); 
    	
    	var model;
    	
    	if(field == 'zone') {
    		model = smt.Model.HealthZone.findOrCreate({id:id});
    		
    		//update provinceSlt
        	this.provinces.fetch({
        		url: appUrl('Province/findAllByZone/'+id),
        		success: _.bind(function(collection, response, options) {
        			var json = this.provinces.toJSON();
        			
        			this.$el.find('#provinceSlt').html(this.provinceSltTemplate(json));
        			
        			//clear 
        			this.searchModel.set('province', null);
        			
        		}, this)
        	})
    		
    	} else if(field == 'province') {
    		model = smt.Model.Province.findOrCreate({id:id});
    	} else if(field == 'networkType') {
    		model = smt.Model.DV_NetworkType.findOrCreate({id:id});
    	} else if(field == 'orgType') {
    		model = smt.Model.DV_OrgType.findOrCreate({id:id});
    	} else {
    		model = null;
    		return;
    	}
    	if(id == 0) {
    		this.searchModel.set(field, null);
    	} else {
    		this.searchModel.set(field, model);
    	}
    	
    },
    resetForm: function() {
    	this.searchModel = new smt.Model.OrganizationNewtork();
    	this.render();
    }, 
    
    render: function() {
    	var json = {};
 
    	this.$el.html(this.searchViewTemplate(json));
    	
    	return this;
	}	

});

var TableResultView = Backbone.View.extend({ 
	initialize: function(options){
		this.tableResultViewTemplate = Handlebars.compile($("#tableResultViewTemplate").html());
		
		this.searchModel = new App.Models.Rfq(); 
		this.searchResults = new App.Pages.Rfqs();
	},
	events: {
		"click .editBtn" : "onClickEditBtn",
		
    	"click .btnPageNav" : "onClickBtnPageNav",
    	"change #pageNavTxt" : "onChangePageNavTxt"
	},
	onChangePageNavTxt : function(e) {

		var newValue = $(e.currentTarget).val();
		var oldValue = $(e.currentTarget).attr('data-value');
		
		if(parseInt(newValue) > 0 && parseInt(newValue) <= this.searchResults.page.totalPages ) {
			
			$(e.currentTarget).attr('data-value', newValue);
			this.renderWithPage(parseInt(newValue));
			
		} else {
			alert('กรุณาระบุหน้าระหว่างเลข 1 ถึง '+ this.searchResults.page.totalPages);
			$(e.currentTarget).val(oldValue);
		}
		
		return false;
	},
    onClickBtnPageNav: function(e) {
    	var pageNum = $(e.currentTarget).attr('data-targetPage');
    	
    	if(pageNum > 0) {
    		this.renderWithPage(pageNum);
    	}
    },
	
	onClickEditBtn: function(e) {
		var rfqId = $(e.currentTarget).parents('tr').attr("data-id");
		appRouter.navigate("showRFQ/"+rfqId, {trigger: true});
	},
	
	renderWithSearchModel: function(searchModel, pageNum) {
		this.searchModel = searchModel;
		this.pageNum = pageNum;
		this.render();
	},
	
	renderWithPage: function(pageNum) {
		this.pageNum = pageNum;
		this.render();
	},
	
	render: function() {
		if(this.searchModel != null) {
			this.searchResults.fetch({
				url: appUrl('Rfq/search/page/' + this.pageNum),
	    		type: 'POST',
	    		data: JSON.stringify(this.searchModel.toJSON()),
	    		dataType: 'json',
	    		contentType: 'application/json',
	    		success: _.bind(function(collection, response, options) {
	    			
					var json = {};
					json.page = this.searchResults.page;
					json.content = this.searchResults.toJSON();
					this.$el.html(this.tableResultViewTemplate(json));
	    		}, this)
	    	});
		}
		return this;
	}
});

var FormView = Backbone.View.extend({
	/**
	 * @memberOf FormView
	 */
	 initialize: function(options){
		 this.showFormViewTemplate = Handlebars.compile($("#showFormViewTemplate").html());
		 this.trFilesTemplate = Handlebars.compile($("#trFilesTemplate").html());
		 
		 Handlebars.registerPartial("trFilesTemplate", $("#trFilesTemplate").html());
	 },
	 events: {
		 "change .formSlt" : "onChangeFormSlt",
		 "change .formTxt" : "onChangeFormTxt",
		 "change .formTxa" : "onChangeFormTxa",
		 "change .formRdo" : "onChangeFormRdo",
		 
		"click #saveFormBtn" : "onClickSaveFormBtn",
		"click #backBtn" : "onClickBackBtn"
			 
	},
	onClickSaveFormBtn: function(e) {
		var validated = true;
		
		// we'll validate 
		this.$el.find('.formTxt').each(function(index, el){
			var id = $(el)
			
			if( ($(el).val() == null ||  $(el).val().length == 0) && 
					$(el).attr('required') != null)	{
				$(el).parents('.form-group').addClass('has-error has-feedback');
				$(el).parent().after('<span class="fa fa-question-circle form-control-feedback"></span>');
				
				validated = false;
				
			}
		});
		
		this.$el.find('.formSlt').each(function(index, el){
			if($(el).val() == 0) {
				$(el).parents('.form-group').addClass('has-error');
				
				validated = false;
				
			}
		});
		
		
		if(!validated) {
			alert ('กรุณากรอกข้อมูลให้ครบถ้วน');
			return false;
		}
		
		this.model.set('teenFriendly', $('input#teenFriendly').is(':checked'));
		
		this.model.save(null, {
			success:_.bind(function(model, response, options) {
				if(response.status != 'SUCCESS') {
					alert(response.status + " :" + response.message);
					
				}
				this.model.set('id', response.data.id);
				alert("บันทึกข้อมูลแล้ว");
				appRouter.navigate("showRFQ/"+this.model.get('id'),{trigger: true});
		},this)});
	},
	
	onClickBackBtn: function(e) {
		appRouter.navigate("search", {trigger: true});
	},
	onChangeFormRdo: function(e) {
		var value = $(e.currentTarget).val();
		var field=$(e.currentTarget).attr('data-field');
		if(value=="0") {
			this.model.set(field, false);
		} else {
			this.model.set(field, true);
		}
	},
	onChangeFormTxa : function(e) {
		var value = $(e.currentTarget).val();

		if(value != null && value.length > 0) {
			// reset error
	    	$(e.currentTarget).parents('.form-group').removeClass('has-error');
	    	$(e.currentTarget).parents('.form-group').find('.form-control-feedback').remove()
		}
		
		var field=$(e.currentTarget).attr('data-field'); 
		this.model.set(field, value);
	},
	onChangeFormTxt : function(e) {
		var value = $(e.currentTarget).val();
		
		if(value != null && value.length > 0) {
			// reset error
	    	$(e.currentTarget).parents('.form-group').removeClass('has-error');
	    	$(e.currentTarget).parents('.form-group').find('.form-control-feedback').remove()
		}
		
		var field=$(e.currentTarget).attr('data-field'); 
		this.model.set(field, value);
	},
	
	onChangeFormSlt: function(e) {
    	//get healthZone val
    	var id=$(e.currentTarget).val();
    	var field=$(e.currentTarget).attr('data-field');
    	
    	// reset error
    	$(e.currentTarget).parents('.form-group').removeClass('has-error');
    	
    	var model;
    	
    	if(field == 'zone') {
    		model = smt.Model.HealthZone.findOrCreate({id:id});
    		
    		//update provinceSlt
        	this.provinces.fetch({
        		url: appUrl('Province/findAllByZone/'+id),
        		success: _.bind(function(collection, response, options) {
        			var json = this.provinces.toJSON();
        			
        			this.$el.find('#provinceSlt').html(this.provinceSltTemplate(json));
        			
        			//clear 
        			this.model.set('province', null);
        			
        		}, this)
        	})
    		
    	} else if(field == 'province') {
    		model = smt.Model.Province.findOrCreate({id:id});
    		
    		//update amphurSlt
        	this.amphurs.fetch({
        		url: appUrl('Province/'+id +'/Amphur'),
        		success: _.bind(function(collection, response, options) {
        			var json = this.amphurs.toJSON();
        			
        			this.$el.find('#amphurSlt').html(this.amphurSltTemplate(json));
        			
        			//clear 
        			this.model.set('amphur', null);
        			
        		}, this)
        	})
    		
    	} else if(field == 'networkType') {
    		model = smt.Model.DV_NetworkType.findOrCreate({id:id});
    	} else if(field == 'orgType') {
    		model = smt.Model.DV_OrgType.findOrCreate({id:id});
    	} else if(field == 'amphur'){
    		model = smt.Model.Amphur.findOrCreate({id: id});
    	} else {
    		return;
    	}
    	this.model.set(field, model);
    	
    },
	
	newForm: function() {
		this.modelId = null;
		this.model = new App.Models.Rfq();
		
		this.render();
	},
	
	
	showForm: function(id) {
		
		console.log('id:' + id);
		this.model = App.Models.Rfq.findOrCreate({id: id});
		$.when(this.model.fetch()).done(_.bind(function(x) {
			this.render();	
		},this));
		
	},
	renderPersonTbl: function() {
		var json = this.model.get('medicalStaffs').toJSON();
		var html = this.medicalStaffsTbodyTemplate(json);
		
		this.$el.find('#medicalStaffsTbody').html(html);
		
	},
	render: function() {
		var json={};
		
		if(this.model.get('id') != null) {
			json.model = this.model.toJSON();
			this.$el.html(this.showFormViewTemplate(json));
		} else {
			json.model={};
			// show input from
			//this.$el.html(this.newFormViewTemplate(json));	
		}
		
		
		$('#fileupload').fileupload({
	        dataType: 'json',
	 
	        done: _.bind(function (e, data) {
	            $.each(data.result, _.bind(function (index, file) {
	            	var file = new App.Models.FileMeta(file);
	            	this.model.get('files').add(file);
	            	var json = {};
	            	json.model = this.model.toJSON();
	            	$('#filesTbl tbody').empty();
	            	$('#filesTbl tbody').html(this.trFilesTemplate(json));
	            },this)); 
	        },this),	 
	        progressall: function (e, data) {
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            $('#progress .bar').css(
	                'width',
	                progress + '%'
	            );
	        }
		});
		
		return this;
	}
});

