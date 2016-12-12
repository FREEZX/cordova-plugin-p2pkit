Use p2pkit with cordova! iOS and Android supported.
Currently limited to enabling and discovery due to my limited available time and scope when starting this, but everything else should be fairly easy to add. PRs and bug reports welcome!

Installing:
```
cordova plugin add cordova-plugin-p2pkit --variable BLUETOOTH_USAGE_DESCRIPTION="your usage message" --save
```

Functions:

```
cordova.plugins.p2pkit.isP2PServicesAvailable(success, error) - Android only
```

```
cordova.plugins.p2pkit.enableP2PKit(apikey, success, error)
```

```
cordova.plugins.p2pkit.createP2pDiscoveryListener(opts)
```
Here, opts is an object with callback functions, include the ones you need:

```
{
    onP2PStateChanged: function(data){},
    onPeerDiscovered: function(data){},
    onPeerLost: function(data){},
    onPeerUpdatedDiscoveryInfo: function(data){},
    onProximityStrengthChanged: function(data){}
}
```

the `data` parameters in onPeer* functions and onProximityStrengthChanged have a `peer` field with `uuid` and `proximityStrength` fields, and onP2PStateChanged has a `state` field which is the returned state id (**These are different in android and ios!**) and they correspond to the values described here: [Android](http://p2pkit.io/javadoc/reference/ch/uepaa/p2pkit/StatusResult.html) [iOS](http://p2pkit.io/appledoc/Constants/PPKPeer2PeerDiscoveryState.html).