<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--3-col">
        </div>

        <div class="mdl-cell mdl-cell--6-col">
            <sec:authorize access="isAnonymous()">
                <form:form action="${pageContext.request.contextPath}/login/create" commandName="user" method="POST" enctype="utf8" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				    
				    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
				    
				        <!-- Form header -->
				        <div class="mdl-card__title">
				            <h2 class="mdl-card__title-text">
				            <spring:message code="login.new.account.title"/>:
				            </h2>
				        </div>
				
				        <!-- Form body -->
				        <div class="mdl-card__supporting-text">
					
					        <!-- Name field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:input id="user-name" path="name" cssClass="mdl-textfield__input"/>
                            		<form:errors id="error-name" path="name" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="user-name"><spring:message code="login.new.account.label.name"/>:</label>
					        </div>
					        
					
					        <!-- E-mail field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:input id="user-email" path="email" cssClass="mdl-textfield__input"/>
                            		<form:errors id="error-email" path="email" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="user-email"><spring:message code="login.new.account.label.email"/>:</label>
					        </div>
					        
					
					        <!-- Password field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:password id="user-password" path="password" cssClass="mdl-textfield__input"/>
                           	 	<form:errors id="error-password" path="password" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="user-password"><spring:message code="login.new.account.label.password"/>:</label>
					        </div>
					        
					
					        <!-- Repeat password field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:password id="user-repeatPassword" path="repeatPassword" cssClass="mdl-textfield__input"/>
                         	   	<form:errors id="error-repeatPassword" path="repeatPassword" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="user-passwordVerification"><spring:message code="login.new.account.label.passwordVerification"/>:</label>
					        </div>
				        </div>
				
				        <!-- Form buttons -->
				        <div class="mdl-card__actions mdl-card--border">
				       	 	<div class="left">
				                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				                    <spring:message code="login.new.account.button.send"/>
				                </button>
				            </div>
				            <div class="right">
				            		<a href="${pageContext.request.contextPath}/login">
								    <button type="button" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					                    <spring:message code="login.new.account.button.login"/>
								    </button>
							    </a>
						    </div>
				        </div>
				    </div>
				</form:form>
            </sec:authorize>
        </div>

        <div class="mdl-cell mdl-cell--3-col">
        </div>
    </div>
</div>