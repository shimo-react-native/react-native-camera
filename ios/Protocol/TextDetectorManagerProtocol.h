//
//  TextDetectorManagerProtocol.h
//  RNCamera
//
//  Created by lisong on 2018/12/13.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@protocol TextDetectorManagerProtocol <NSObject>

- (instancetype)init;

-(BOOL)isRealDetector;
-(NSArray *)findTextBlocksInFrame:(UIImage *)image scaleX:(float)scaleX scaleY:(float) scaleY;

@end
