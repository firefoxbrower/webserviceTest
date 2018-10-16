/*
*
*控件名称：通用树型控件
*说        明  :该控件是从ExtJs的TreePanel继承过来的
*控件版本：1.0
*调整记录：
*
*历史版本：
*/
Ext.namespace("Ext.matech");
Ext.QuickTips.init();
var DEFAULT_TREE_URL =TEMP_SYSTEM_WEB_ROOT+ "/common.do?method=getTreeJsonData";

function treeNodeExpandOrCollap(_tree,_node,_level,_type){
	var node=_node||_tree.getSelectionModel().getSelectedNode();
	//0.展开,1.收回0)
	if(_type==0){
		if(node){
			node.expand();
			node.expandChildNodes(true);
		}else{
			_tree.expandAll();
		}
	}
	if(_type==1){
		if(node){
			node.collapseChildNodes(true);
			node.collapse();
		}else{
			_tree.collapseAll();
		}			
	}
	if(_type==2){
		var _root=_tree.getRootNode();
		_root.expand();
		_root.cascade(function(n){
			if(n.getDepth()<_level && n.hasChildNodes() && !n.isExpanded()){
				n.expand();	
			}
			if(n.getDepth()>=_level){
				n.collapse();
			}
		},this);
	}
}
function treeNodeSearch(_tree,_value,_attr){
	if(_tree.searchFun){
		_tree.searchFun(_tree,_value,_attr);
	}else{
		_tree.filter.filter(_value,_attr);
	}
}

function treeNodeExpandByID(_tree,_nodeId){
	var flag=false;
	var _root=_tree.getRootNode();
	_root.expand();
	_root.cascade(function(n){
		if(n.id==_nodeId){
			flag=true;
			return
		}
		if(!flag && n.hasChildNodes() && !n.isExpanded()){
			n.expand();	
		}
	},this);
	if(flag){
		_tree.getNodeById(_nodeId).select();
	}
} 

function treeNodeCheckAll(_tree,checked){
	if(!_tree){
		return;
	}
	var _node=_tree.getSelectionModel().getSelectedNode();
	if(!_node){
		_node=_tree.getRootNode();	
	}else{
		if(_node.parentNode){
			_node.attributes.checked = checked;
			_node.getUI().checkbox.checked = checked;			
		}
	}
	_node.cascade(function(n){
		if(n.parentNode && n.parentNode.isExpanded()){
			n.getUI().checkbox.checked = checked; 
			n.attributes.checked = checked;			
		}
	},this);
	
}

function treeNodeCheckClear(_tree,checked){
	if(!_tree){
		return;
	}
	var _node=_tree.getRootNode();	
	_node.cascade(function(n){
		if(n.parentNode){
			n.getUI().checkbox.checked = checked; 
			n.attributes.checked = checked;			
		}
	},this);	
}

Ext.matech.TreePanel = Ext.extend(Ext.tree.TreePanel, {
	animate : true,
	rootVisible : true,
	autoScroll : true,
	border:false,
	layout:'fit',
	multilevel:true,
	rowData:false,
	checked:false,
	enableDD:false,
	cascadeParent:true,
	cascadeChildren:true,
	ctxmenusDisable:{},
	treeGrid:false,
	allowDefaultTbar:false,
	allowDefaultBbar:false,
	allowDefaultBbar2:false,
	checkSelect:true,
	
	listeners : {
		'render':function(){
			var self=this;
			if(self.getTopToolbar() && self.allowDefaultTbar){
				var _data=[[1,1],[2,2],[3,3],[4,4],[5,5],[6,6],[7,7],[8,8],[9,9],[10,10]];
				var level=new Ext.form.ComboBox({
						 width:60,
						 listWidth:60,
						 store: new Ext.data.SimpleStore({
							 	fields: ['value', 'text'],
							 	data : _data
						 	 }),
						 valueField:'value',
						 displayField:'text',
						 typeAhead: true,
						 mode: 'local',
						 triggerAction: 'all',
						 editable:false,
						 selectOnFocus:false,//用户不能自己输入,只能选择列表中有的记录
						 allowBlank:false,
				         listeners:{'select':function(){
				        	 treeNodeExpandOrCollap(self,null,this.getValue(),2);
				         }}
					});
				
				if(self.checked=="true" && !self.cascadeChildren){
					self.getTopToolbar().add({
						text:'清空',
				   		icon:TEMP_SYSTEM_WEB_ROOT + '/img/delete.gif',
				   		 handler:function(){
				   			treeNodeCheckClear(self,false);
				   		 }
					},'-',{
						text:'全选',
				   		icon:TEMP_SYSTEM_WEB_ROOT + '/img/selectall.gif',
				   		 handler:function(){
				   			treeNodeCheckAll(self,true) ;
				   		 }
					},'-') ;
				}
				
				self.getTopToolbar().add([
				                          {text:'',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllExpand.png',handler:function(){treeNodeExpandOrCollap(self,null,level.getValue(),0);}},
				                          '-',
				                          {text:'',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllCollaps.png',handler:function(){treeNodeExpandOrCollap(self,null,level.getValue(),1);}},
				                          '-','级次:',level,
				                          '->',{text:'',icon:TEMP_SYSTEM_WEB_ROOT+'/img/refresh.gif',handler:function(){self.refresh();}}
				                          ]);
				
				self.getTopToolbar().doLayout();
				level.setValue(1);
			}
			
			if(self.getBottomToolbar() && self.allowDefaultBbar){
				var _inputId='search'+self.id;
				var _width=100;
				if(self.width){
					_width=self.width-30;
				}
				var _input=new Ext.form.TextField({id:_inputId,
												   width:_width,
												   listeners:{    
												            specialkey:function(field,e){    
												                if (e.getKey()==Ext.EventObject.ENTER){    
												                	treeNodeSearch(self,Ext.getCmp(_inputId).getValue());
												                }    
												            } }
												 });
				self.getBottomToolbar().add(_input);
				self.getBottomToolbar().add([{text:'',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeSearch.gif',handler:function(){treeNodeSearch(self,Ext.getCmp(_inputId).getValue());}}]);
			}
			
			if(self.getBottomToolbar() && self.allowDefaultBbar2){
				var _inputId='search'+self.id;
				var _width=100;
				if(self.width){
					_width=self.width-30;
				}
				var _input=new Ext.form.TextField({id:_inputId,
												   width:_width,
												   listeners:{    
												            specialkey:function(field,e){    
												                if (e.getKey()==Ext.EventObject.ENTER){    
												                	treeNodeSearch(self,Ext.getCmp(_inputId).getValue());
												                }    
												            } }
												 });
				//self.getBottomToolbar().add(_input);
				self.getBottomToolbar().add([
				                              '->','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                   '->',{text:'下一步',icon:TEMP_SYSTEM_WEB_ROOT+'/img/save.gif',
												handler:function(){
													addsave();
											    }
				                             }
												]);
			}
			
			
		},
		resize:function(){
			var _width=this.getWidth();
			var self=this;
			var _inputId='search'+self.id;
			if(_width>100){
				if(self.allowDefaultBbar){
					Ext.getCmp(_inputId).setWidth(_width-30);
				}
			}
		}
	},
	initComponent : function() {
		var me = this;
		
		if(me.allowDefaultTbar){
			me.tbar=[];
		}
		if(me.allowDefaultBbar){
			me.bbar=[];	
		}
		if(me.allowDefaultBbar2){
			me.bbar=[];	
		}

		Ext.matech.TreePanel.superclass.initComponent.call(this);
		
		this.loader = new Ext.tree.TreeLoader({
			dataUrl : me.dataUrl?me.dataUrl:DEFAULT_TREE_URL
		});

		this.loader.on('beforeload', function(treeLoader, node) {
			
	   	  	var refer = me.refer;
		  	var refer1 = me.refer1;
		  	var refer2 = me.refer2;
			var refer3 = me.refer3;
		  	
		  	var referObj = document.getElementById(refer) ;
		  	var refer1Obj = document.getElementById(refer1) ;
		  	var refer2Obj = document.getElementById(refer2) ;
		  	
		  	if(referObj) {
		  		refer = referObj.value ; 
		  	}
		  	if(refer1Obj) {
		  		refer1= refer1Obj.value ; 
		  	}
		  	if(refer2Obj) {
		  		refer2 = refer2Obj.value ; 
		  	}		
			
			this.loader.baseParams.node = node.id;
			this.loader.baseParams.autoid = me.autoid;
			this.loader.baseParams.rowData = me.rowData;
			this.loader.baseParams.loadAll = me.loadAll;
			this.loader.baseParams.attr = me.attr;
			this.loader.baseParams.treeGrid=me.treeGrid;
			this.loader.baseParams.multilevel=me.multilevel;
			this.loader.baseParams.checked=me.checked;
			this.loader.baseParams.refer=refer;//$2
			this.loader.baseParams.refer1=refer1;//$3
			this.loader.baseParams.refer2=refer2;//$4
			this.loader.baseParams.refer3=refer3;//$5
			
			if(me.param){
				for(var paramObj in me.param){
					this.loader.baseParams[paramObj]=me.param[paramObj];					
				}
			}
		}, this);
		
		this.filter = new Ext.ux.form.TreeFilter(me,{
			ignoreFolder:me.ignoreFolder,
			clearAction:'collapse'
		});
		
		var treeNodeEvent=new Ext.plugin.tree.TreeNodeChecked({   
			firstInit:false,
	        // 级联选中   
	        cascadeCheck: true,   
	        // 级联父节点   
	        cascadeParent:me.cascadeParent,   
	        // 级联子节点   
	        cascadeChild:me.cascadeChildren, 
	        // 连续选中   
	        linkedCheck: true,   
	        // 异步加载时，级联选中下级子节点   
	        asyncCheck: true  
	    });
		
		this.plugins=[treeNodeEvent],
		
		//单击展开叶子节点
		this.on('click',function(node,event){ 
			event.stopEvent();
	    	if (!node.isLeaf()){//非叶子	    		
				node.expand();
			}
	    	if(me.onclick){
	    		me.onclick(node,event);
	    	};
		}); 
		
		this.on('dblclick',function(node,event) {
	 		if(me.ondbclick){
	 			if(typeof me.ondbclick =="function"){
	 				me.ondbclick(node,event);
		 		}
	 		}
	 	});
		
		this.on('checkchange',function(node){
	    	if(me.checkchange){
	    		me.checkchange(node,event);
	    	};			
	    	if(me.checkSelect){
	    		node.select();
	    	}
	 	});
	 	
		//节点拖动
		this.on('beforemovenode', function(tree, tt, oldParent,
	             newParent, index,node) {	
			if(me.beforemovenode){
				me.beforemovenode(tree,tt,oldParent,newParent,index,node);
			}else{
				//当被拖动节点在拖动前所在位置的父节点=拖动后停放位置的父节点，即在同一个目录小拖动
				 if(oldParent.id == newParent.id){
					 return true;
				 }  
		         return false;				
			}
	     });
	
		 this.on('nodedrop', function(obj) {
			 if(me.nodedrop){
				 me.nodedrop(obj);
			 }else{
		        if (obj.dropNode.parentNode == obj.target.parentNode) {
			          var parentId=obj.dropNode.parentNode;
			          var nodes=parentId.childNodes;
			          var values = "";
			          for(var i=0;i<nodes.length;i++){
			        	  if(i==0){
			        		  values=nodes[i].id; 
			        	  }else{
			        		  values += "," + nodes[i].id;  
			        	  }
			          }
			          //修改节点排序号
			          Ext.Ajax.request( {
			               url : this.sortUrl+'&values='+values
			          });
			          return true;
			    	}
			    	return false;				 
			 }
		});
		var _ctxmenu=[]; 
		//点击右键出现tree菜单
		if(me.contextmenu){
			_ctxmenu=_ctxmenu.concat(me.contextmenu);
			
			 this.on('contextmenu',function(node, e) {				   
				   node.select();//点击右键同时选中该项   
				   e.preventDefault(); 
				   
				   if(me.ctxmenuClick){
					   try{
						   me.ctxmenuClick(node,e);
					   }catch(e){
						   
					   }
				   }
				   
				   for(var i=0;i<_ctxmenu.length;i++){
					   for(var obj in me.ctxmenusDisable){
						   if(obj==_ctxmenu[i].text){
							   if(me.ctxmenusDisable[obj]){
								   _ctxmenu[i]["disabled"]=true;
							   }else{
								   _ctxmenu[i]["disabled"]=false;
							   };
							   break;
						   }
					   }
				   }
				   var treeMenu = new Ext.menu.Menu(_ctxmenu);  
				   //定位菜单的显示位置   
				   treeMenu.showAt(e.getPoint());  			 
				 });		
		
		}

	},
	setCtxmenuDisable:function(menutext,isdisable){
		this.ctxmenusDisable[menutext]=isdisable;
	},
	refresh:function(){
		 //获取选中的节点
		 var node = this.getSelectionModel().getSelectedNode(); 
		 var self=this;
		 if(node){
		     var path = node.getPath('id'); 
		     //展开指定节点
		     self.getLoader().load(self.root,function () {self.expandPath(path,'id',function(){ 
		    	 	self.selectPath(path,'id');//回选节点
				 });
			 });		 
		 }else{
			 self.root.reload();
		 }		
	},
	refreshById:function(nodeId){
		
		var self=this;
		var path=self.getNodeById(nodeId).getPath('id');
		//展开指定节点
	    self.getLoader().load(self.root,function () {self.expandPath(path,'id',function(){ 
	    	 	self.selectPath(path,'id');//回选节点
			 });
		 });
	},
	//获取节点
	getSelectNode:function(){
		 var node = this.getSelectionModel().getSelectedNode(); 
		 if(node){
		     return node.id; 
		 }
		 return "";
	},
	//获取节点路径字符串
	getSelectNotPath:function(){
		 var node = this.getSelectionModel().getSelectedNode(); 
		 var path="";
		 if(node){
		     path = node.getPath('id'); 
		 }
		 return path;
	},
	//获取所有选择的节点字符串
	getCheckedNoteStr:function(isContainParentNote,isContainDisable){
		var msg="";
		var selNodes = this.getChecked();
	    Ext.each(selNodes, function(node){
	         if(msg.length > 0){
	             msg += ',';
	         }
	         if(isContainParentNote){
	        	 var parentNode=node.parentNode;
	        	 var parentNodeId;
	        	 if(parentNode.attributes.checked){
	        		 parentNodeId=parentNode.id;
	        	 }else{
	        		 parentNodeId="none";
	        	 };
	        	 if(isContainDisable){
	        		 msg += node.id+"`"+parentNodeId; 
	        	 }else{
	        		 if(!node.disabled){
	        			 msg += node.id+"`"+parentNodeId;  
	        		 }else{
	        			 msg=msg.substring(0, msg.length-1);
	        		 } 
	        	 }
	         }else{
	        	 if(isContainDisable){
	        		 msg += node.id;
	        	 }else{
	        		 if(!node.disabled){
	        			 msg += node.id;  
	        		 }else{
	        			 msg=msg.substring(0, msg.length-1); 
	        		 } 
	        	 } 
	         }	        
	    });		
	    return msg;
	},
	//获取所有选择的叶子节点字符串
	getLeafCheckedNoteStr:function(){
		var msg="";
		var selNodes = this.getLeafChecked();
	    Ext.each(selNodes, function(node){
	         if(msg.length > 0){
	             msg += ',';
	         }
	         msg += node.id;
	    });		
	    return msg;
	},
	getRowData:function(node){
		var objs=node.attributes.rowData;
		if(typeof objs=="string"){
			return Ext.util.JSON.decode(objs)[0];
		}else{
			return objs[0];
		}
	},
	getNoteStr:function(parentNote,isSingle){
		var self=this;
		var result="";
		var subResult="";
		
		if(!isSingle){
			isSingle=false;
		}
		
		if(!parentNote){
			return result;
		}
		if(!parentNote.hasChildNodes()){
			return result;
		}
		Ext.each(parentNote.childNodes, function(node) {
			if(result==""){
				if(isSingle){
					result=node.id; 							
				}else{
					result=node.id+"`"+node.parentNode.id+"`"+node.getDepth(); 			
				}
			}else{
				if(isSingle){
					result=result+","+node.id; 							
				}else{
					result=result+","+node.id+"`"+node.parentNode.id+"`"+node.getDepth(); 						
				}
			}
			subResult=self.getNoteStr(node,isSingle);
			if(subResult!=""){
				result=result+","+subResult;
			}
		});
		return result;
	},
	getChildNoteStr:function(parentNote,isSingle){
		var self=this;
		var result="";
		var subResult="";
		var parentresult= "";
		if(!isSingle){
			isSingle=false;
		}
		
		if(!parentNote){
			return parentresult;
		}
		if(!parentNote.hasChildNodes()){
			return parentresult;
		}
		Ext.each(parentNote.childNodes, function(node) {
			
				if(result==""){
					if(isSingle){
						result=node.id; 							
					}else{
						result=node.id+"`"+node.parentNode.id+"`"+node.getDepth(); 			
					}
				}else{
					if(isSingle){
						result=result+","+node.id; 							
					}else{
						result=result+","+node.id+"`"+node.parentNode.id+"`"+node.getDepth(); 						
					}
				}
				subResult=self.getNoteStr(node,isSingle);
				if(subResult!=""){
					parentresult=parentresult+","+subResult;
				}
			
		});
		return parentresult;
	},
	//删除单选节点
	removeNode:function(node){
		if(node){
			try{
				node.remove();
				node.unselect();
			}catch(err){
				alert("传入参数不是节点对象!");
			}
		}else{
			var selectNode = this.getSelectionModel().getSelectedNode();
			if(selectNode){
				selectNode.remove();
				selectNode.unselect();
			}else{
				alert("您还没有选中节点!");
			}
		}
	},
	//删除多选节点
	removeMutiNode:function(){
		var self=this;
		var selNodes = self.getChecked();
	    Ext.each(selNodes, function(node){
	    	if(tree.getNodeById(node.id)){
	    		parentNode=self.getNodeById(node.parentNode.id);
	    		parentNode.removeChild(node);
	    	};
	    });			
	},
	//不知道为什么删除不了
	removeMutiNode2:function(){
		var self=this;
		var selNodes = self.getChecked();
	    Ext.each(selNodes, function(node){
	    		parentNode=self.getNodeById(node.parentNode.id);
	    		parentNode.removeChild(node);
	    });			
	},
	nodeChecked:function(node,isChecked){
		node.getUI().checkbox.checked=isChecked;
		node.attributes.checked=isChecked;
	}
});
/*
*
*控件名称：通用树型列表控件
*说        明  :该控件是从ExtJs的TreeGrid插件继承过来的
*控件版本：1.0
*调整记录：
*
*历史版本：
*/
Ext.matech.TreeGrid = Ext.extend(Ext.ux.tree.TreeGrid,{
    autoScroll:true,//这里设置为true，然后加上布局就显示滚动条
    enableDD: false,
	enableSort : false,
	sortable:false,
	multilevel:true,
	rowData:false,
	checked:false,
	lines:true,
	useArrows:false,
	treeGrid:true,
	cascadeParent:true,
	cascadeChildren:true,
	allowDefaultBbar:true,
	allowDefaultBbar2:false,
	ctxmenusDisable:{},

	listeners : {
		'render':function(){
			var self=this;
			if(self.getBottomToolbar() && self.allowDefaultBbar){

				var _data=[[1,1],[2,2],[3,3],[4,4],[5,5],[6,6],[7,7],[8,8],[9,9],[10,10]];
				var level=new Ext.form.ComboBox({
						 width:60,
						 listWidth:60,
						 store: new Ext.data.SimpleStore({
							 	fields: ['value', 'text'],
							 	data : _data
						 	 }),
						 valueField:'value',
						 displayField:'text',
						 typeAhead: true,
						 mode: 'local',
						 triggerAction: 'all',
						 editable:false,
						 selectOnFocus:false,//用户不能自己输入,只能选择列表中有的记录
						 allowBlank:false,
				         listeners:{'select':function(){
				        	 treeNodeExpandOrCollap(self,null,this.getValue(),2);
				         }}
					});
				
				self.getBottomToolbar().add([
				                          {text:'全部展开',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllExpand.png',handler:function(){treeNodeExpandOrCollap(self,null,level.getValue(),0);}},
				                          '-',
				                          {text:'全部折叠',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllCollaps.png',handler:function(){treeNodeExpandOrCollap(self,null,level.getValue(),1);}},
				                          '-','展开级次:',level
				                          ]);
				
				
				level.setValue(1);
				
				var _attr=[];
				Ext.each(self.columns,function(col){
					if(col.hidden){}else{
						_attr.push(col.dataIndex);
					};
				});
				
				var _inputId='search'+self.id;
				var _input=new Ext.form.TextField({id:_inputId,
												   width:150,
												   listeners:{    
												            specialkey:function(field,e){    
												                if (e.getKey()==Ext.EventObject.ENTER){    
												                	treeNodeSearch(self,Ext.getCmp(_inputId).getValue(),_attr);
												                }    
												            } }
												 });
				self.getBottomToolbar().add(_input);
				self.getBottomToolbar().add([{text:'搜索',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeSearch.gif',handler:function(){treeNodeSearch(self,Ext.getCmp(_inputId).getValue(),_attr);}}]);
				
				self.getBottomToolbar().doLayout();
			}
			if(self.getBottomToolbar() && self.allowDefaultBbar2){

				var _data=[[1,1],[2,2],[3,3],[4,4],[5,5],[6,6],[7,7],[8,8],[9,9],[10,10]];
				var level=new Ext.form.ComboBox({
						 width:60,
						 listWidth:60,
						 store: new Ext.data.SimpleStore({
							 	fields: ['value', 'text'],
							 	data : _data
						 	 }),
						 valueField:'value',
						 displayField:'text',
						 typeAhead: true,
						 mode: 'local',
						 triggerAction: 'all',
						 editable:false,
						 selectOnFocus:false,//用户不能自己输入,只能选择列表中有的记录
						 allowBlank:false,
				         listeners:{'select':function(){
				        	 treeNodeExpandOrCollap(self,null,this.getValue(),2);
				         }}
					});
				
				self.getBottomToolbar().add([
				                          {text:'全部折叠',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllCollaps.png',
				                        	  handler:function(){
				                        		  if(this.text=="全部折叠"){
				                        			  this.setText("全部展开");
					                        		  this.setIcon(TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllExpand.png');
					                        		  treeNodeExpandOrCollap(self,null,level.getValue(),1);
				                        		  }else if (this.text=="全部展开"){
					                        			  
					                        			  this.setText("全部折叠");
						                        		  this.setIcon(TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllCollaps.png');
						                        		  treeNodeExpandOrCollap(self,null,level.getValue(),0);
						                        		  
					                        	 }
				                        	  }
				                        		 
				                          	},
				                          /*'-',
				                          {text:'全部折叠',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeAllCollaps.png',
				                          		handler:function(){
				                          			treeNodeExpandOrCollap(self,null,level.getValue(),1);}
				                          	    },*/
				                          '-','输入搜索内容:'
				                          ]);
				
				
				level.setValue(1);
				
				var _attr=[];
				Ext.each(self.columns,function(col){
					if(col.hidden){}else{
						_attr.push(col.dataIndex);
					};
				});
				
				var _inputId='search'+self.id;
				var _input=new Ext.form.TextField({id:_inputId,
												   width:150,
												   listeners:{    
												            specialkey:function(field,e){    
												                if (e.getKey()==Ext.EventObject.ENTER){    
												                	treeNodeSearch(self,Ext.getCmp(_inputId).getValue(),_attr);
												                }    
												            } }
												 });
				self.getBottomToolbar().add(_input);
				self.getBottomToolbar().add([{text:'搜索',icon:TEMP_SYSTEM_WEB_ROOT+'/img/tree/treeSearch.gif',handler:function(){treeNodeSearch(self,Ext.getCmp(_inputId).getValue(),_attr);}}]);
				
				self.getBottomToolbar().doLayout();
			}
			
		}
	},	
	//初始化
	initComponent : function() {
		var me = this;
		
		if(me.allowDefaultBbar){
			me.bbar=[];	
		}
		if(me.allowDefaultBbar2){
			me.bbar=[];	
		}
		
		Ext.matech.TreeGrid.superclass.initComponent.call(this);
		
		this.loader = new Ext.tree.TreeLoader({
			dataUrl : me.dataUrl?me.dataUrl:DEFAULT_TREE_URL
		});
		this.loader.on('beforeload', function(treeLoader, node) {
			this.loader.baseParams.node = node.id;
			this.loader.baseParams.autoid = me.autoid;
			this.loader.baseParams.rowData = me.rowData;
			this.loader.baseParams.loadAll = me.loadAll;
			this.loader.baseParams.multilevel=me.multilevel;
			this.loader.baseParams.treeGrid=me.treeGrid;
			this.loader.baseParams.checked=me.checked;
			this.loader.baseParams.refer=me.refer;//$2
			this.loader.baseParams.refer1=me.refer1;//$3
			this.loader.baseParams.refer2=me.refer2;//$4
			this.loader.baseParams.refer3=me.refer3;//$4
			if(me.param){
				for(var paramObj in me.param){
					this.loader.baseParams[paramObj]=me.param[paramObj];					
				}
			}
		}, this);
		
		this.filter = new Ext.ux.form.TreeFilter(me,{
			ignoreFolder:me.ignoreFolder,
			clearAction:'collapse'
		});
		
		this.plugins=[new Ext.plugin.tree.TreeNodeChecked({   
	        // 级联选中   
	        cascadeCheck: true,   
	        // 级联父节点   
	        cascadeParent:me.cascadeParent,   
	        // 级联子节点   
	        cascadeChild:me.cascadeChildren, 
	        // 连续选中   
	        linkedCheck: true,   
	        // 异步加载时，级联选中下级子节点   
	        asyncCheck: true  
	    })],
	   
		//单击展开叶子节点
		this.on('click',function(node,event){ 
			event.stopEvent();		
	    	if (!node.isLeaf()){//非叶子	    		
				node.expand();
			}
	    	if(me.onclick){
	    		me.onclick(node,event);
	    	};
		}); 
		
		this.on('dblclick',function(node,event) {
	 		if(me.ondbclick){
	 			if(typeof me.ondbclick =="function"){
	 				me.ondbclick(node,event);
		 		}
	 		}
	 	});
		
		//节点拖动
		this.on('beforemovenode', function(tree, tt, oldParent,
	             newParent, index,node) {	
			if(me.beforemovenode){
				me.beforemovenode(tree,tt,oldParent,newParent,index,node);
			}else{
				//当被拖动节点在拖动前所在位置的父节点=拖动后停放位置的父节点，即在同一个目录小拖动
				 if(oldParent.id == newParent.id){
					 return true;
				 }  
		         return false;				
			}
	     });
	
		 this.on('nodedrop', function(obj) {
			 if(me.nodedrop){
				 me.nodedrop(obj);
			 }else{
		        if (obj.dropNode.parentNode == obj.target.parentNode) {
			          var parentId=obj.dropNode.parentNode;
			          var nodes=parentId.childNodes;
			          var values = "";
			          for(var i=0;i<nodes.length;i++){
			        	  if(i==0){
			        		  values=nodes[i].id; 
			        	  }else{
			        		  values += "," + nodes[i].id;  
			        	  }
			          }
			          //修改节点排序号
			          Ext.Ajax.request( {
			               url : this.sortUrl+'&values='+values
			          });
			          return true;
			    	}
			    	return false;				 
			 }
		});
		 
		//点击右键出现tree菜单   
		if(me.contextmenu){
			 this.on('contextmenu',function(node, e) {				   
				   node.select();//点击右键同时选中该项   
				   e.preventDefault(); 
				   
				   if(me.ctxmenuClick){
					   try{
						   me.ctxmenuClick(node,e);
					   }catch(e){
						   
					   }
				   }
				   
				   for(var i=0;i<me.contextmenu.length;i++){
					   for(var obj in me.ctxmenusDisable){
						   if(obj==me.contextmenu[i].text){
							   if(me.ctxmenusDisable[obj]){
								   me.contextmenu[i]["disabled"]=true;
							   }else{
								   me.contextmenu[i]["disabled"]=false;
							   };
							   break;
						   }
					   }
				   }
				   var treeMenu = new Ext.menu.Menu(me.contextmenu);  
				   //定位菜单的显示位置   
				   treeMenu.showAt(e.getPoint());  			 
				 });			
		}
		
		
	},
	setCtxmenuDisable:function(menutext,isdisable){
		this.ctxmenusDisable[menutext]=isdisable;
	},
	//展开指点节点
	refresh:function(_path){
		var self=this;
		if(_path){
		     //展开指定节点
			self.expandPath(_path,'id',function(){ 
	    	 	self.selectPath(_path,'id');//回选节点
			 });
		}else{
			 //获取选中的节点
			 var node = this.getSelectionModel().getSelectedNode(); 
			 if(node){
			     path = node.getPath('id'); 
			     //展开指定节点
			     self.getLoader().load(self.root,function () {self.expandPath(path,'id',function(){ 
			    	 	self.selectPath(path,'id');//回选节点
					 });
				 });		 
			 }else{
				 self.root.reload();
			 }				
		}
	},
	//展开指点节点
	refreshById:function(nodeId){
		var self=this;
		var _path=self.getNodeById(nodeId).getPath('id');
		
		if(_path){
		     //展开指定节点
			self.expandPath(_path,'id',function(){ 
	    	 	self.selectPath(_path,'id');//回选节点
			 });
		}else{
			 //获取选中的节点
			 var node = this.getSelectionModel().getSelectedNode(); 
			 if(node){
			     path = node.getPath('id'); 
			     //展开指定节点
			     self.getLoader().load(self.root,function () {self.expandPath(path,'id',function(){ 
			    	 	self.selectPath(path,'id');//回选节点
					 });
				 });		 
			 }else{
				 self.root.reload();
			 }				
		}
	},
	//获取节点
	getSelectNode:function(){
		 var node = this.getSelectionModel().getSelectedNode(); 
		 if(node){
		     return node.id; 
		 }
		 return "";
	},
	//获取节点路径字符串
	getSelectNotPath:function(){
		 var node = this.getSelectionModel().getSelectedNode(); 
		 var path="";
		 if(node){
		     path = node.getPath('id'); 
		 }
		 return path;
	},
	//获取所有选择的节点字符串
	getCheckedNoteStr:function(){
		var msg="";
		var selNodes = this.getChecked();
	    Ext.each(selNodes, function(node){
	         if(msg.length > 0){
	             msg += ',';
	         }
	         msg += node.id;
	    });		
	    return msg;
	},
	//获取所有选择的叶子节点字符串
	getLeafCheckedNoteStr:function(){
		var msg="";
		var selNodes = this.getLeafChecked();
	    Ext.each(selNodes, function(node){
	         if(msg.length > 0){
	             msg += ',';
	         }
	         msg += node.id;
	    });		
	    return msg;
	},
	getRowData:function(node){
		var objs=node.attributes.rowData;
		if(typeof objs=="string"){
			return Ext.util.JSON.decode(objs)[0];
		}else{
			return objs[0];
		}
	},
	getNoteStr:function(parentNote,isSingle){
		var self=this;
		var result="";
		var subResult="";
		if(!isSingle){
			isSingle=false;
		}
		if(!parentNote){
			return result;
		}
		if(!parentNote.hasChildNodes()){
			return result;
		}
		Ext.each(parentNote.childNodes, function(node) {
			if(result==""){
				if(isSingle){
					result=node.id; 							
				}else{
					result=node.id+"`"+node.parentNode.id+"`"+node.getDepth(); 			
				}
			}else{
				if(isSingle){
					result=result+","+node.id; 							
				}else{
					result=result+","+node.id+"`"+node.parentNode.id+"`"+node.getDepth(); 						
				}
			}
			subResult=self.getNoteStr(node,isSingle);
			if(subResult!=""){
				result=result+","+subResult;
			}
		});
		return result;
	},
	//删除单选节点
	removeNode:function(node){
		if(node){
			try{
				node.remove();
				node.unselect();
			}catch(err){
				alert("传入参数不是节点对象!");
			}
		}else{
			var selectNode = this.getSelectionModel().getSelectedNode();
			if(selectNode){
				selectNode.remove();
				selectNode.unselect();
			}else{
				alert("您还没有选中节点!");
			}
		}
	},
	//删除多选节点
	removeMutiNode:function(){
		var self=this;
		var selNodes = self.getChecked();
	    Ext.each(selNodes, function(node){
	    	if(tree.getNodeById(node.id)){
	    		parentNode=self.getNodeById(node.parentNode.id);
	    		parentNode.removeChild(node);
	    	};
	    });			
	},
	nodeChecked:function(node,isChecked){
		node.getUI().checkbox.checked=isChecked;
		node.attributes.checked=isChecked;
	}
});