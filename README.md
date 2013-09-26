Play 2.2.x module that supplies a Session Cache
=============================================

This library supplies a wrapper for actions that can be used to associate a 
cache with a specific user. This is similar to the session in servlet type 
applications.

Under the hood the cache that is provided is nothing more than a wrapper 
around the default Play 2 Cache that makes sure that every key is supplied 
with a prefix.

If the user has no 'id' associated (no sessionId cookie), a cookie will be 
set. When the user has an 'id', that 'id' will be used to create the cache 
wrapper. 

Since we are using the default Cache, it's up to you to specify timeouts. 
Unlike servlet type session mechanisms there is no global timeout. Even 
the cookie does not have a limit, it simply contains an 'id'.


Note that this is developed with and for *Scala*.

Installation
------------

Add a resolver to your project settings:

``` scala
val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += Resolver.url("My GitHub Play Repository", url("http://krzysztofkowalski.github.io/releases/"))(Resolver.ivyStylePatterns)
)
```

Add the dependency:

``` scala
	val appDependencies = Seq(
      "com.github.krzysztofkowalski" %% "play-session-cache" % "1.2.0"
    )
```

For Play 2.1.x use this version

``` scala
	"nl.rhinofly" % "session-cache_2.10" % "1.1.3",
```

Usage 
------------

``` scala
object Application extends Controller {

  def index = SessionCache { sessionCache =>
    Action {
      val userIdOption = sessionCache get "userId"
    
      Ok(views.html.index(someValueFromTheCache))
    }
  }
  
}

```

## License info

Forked from https://github.com/Rhinofly/play-libraries/
