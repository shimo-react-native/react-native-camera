//
//  RNFaceDetectorStub.h
//  RCTCamera
//
//  Created by Joao Guilherme Daros Fidelis on 24/01/18.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

#import "RNFaceDetectorManagerProtocol.h"

@interface RNFaceDetectorManagerStub : NSObject<RNFaceDetectorManagerProtocol>

- (NSDictionary *)constantsToExport;
+ (NSDictionary *)constants;

- (instancetype)initWithSessionQueue:(dispatch_queue_t)sessionQueue delegate:(id <RNFaceDetectorDelegate>)delegate;

- (void)setIsEnabled:(id)json;
- (void)setLandmarksDetected:(id)json;
- (void)setClassificationsDetected:(id)json;
- (void)setMode:(id)json;

- (void)maybeStartFaceDetectionOnSession:(AVCaptureSession *)session withPreviewLayer:(AVCaptureVideoPreviewLayer *)previewLayer;
- (void)stopFaceDetection;

@end
