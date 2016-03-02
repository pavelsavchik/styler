//environments {
//    production {
//        dataSource {
//            dbCreate = "update"
//            driverClassName = "org.postgresql.Driver"
//            dialect = org.hibernate.dialect.PostgreSQLDialect
//            uri = new URI(System.env.DATABASE_URL?:"postgres://localhost:5432/test")
//            url = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require"
//            username = uri.userInfo.split(":")[0]
//            password = uri.userInfo.split(":")[1]
//        }
//    }
//}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.satch.domain.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.satch.domain.UserRole'
grails.plugin.springsecurity.authority.className = 'com.satch.domain.Role'
grails.plugin.springsecurity.interceptUrlMap = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	[pattern: '/applicationInfo', access: ['ROLE_ADMIN']],
	[pattern: '/products/**', access: ['permitAll']],
	[pattern: '/classifications/**', access: ['permitAll']],
	[pattern: '/stores/**', access: ['permitAll']],
	[pattern: '/messages/**', access: 'isAuthenticated()']
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

