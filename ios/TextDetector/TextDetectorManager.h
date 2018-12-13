#if __has_include(<GoogleMobileVision/GoogleMobileVision.h>)
#import <GoogleMobileVision/GoogleMobileVision.h>

#import "TextDetectorManagerProtocol.h"

@interface TextDetectorManager : NSObject<TextDetectorManagerProtocol>

- (instancetype)init;

-(BOOL)isRealDetector;
-(NSArray *)findTextBlocksInFrame:(UIImage *)image scaleX:(float)scaleX scaleY:(float) scaleY;

@end
#endif
